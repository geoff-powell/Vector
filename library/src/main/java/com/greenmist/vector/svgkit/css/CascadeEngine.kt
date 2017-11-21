package com.greenmist.vector.svgkit.css

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.matchers.ClassElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.parser.CssImportant
import com.greenmist.vector.svgkit.css.parser.CssStylesheet
import com.greenmist.vector.svgkit.css.results.CascadeResult
import com.greenmist.vector.svgkit.css.rules.BaseRule
import com.greenmist.vector.svgkit.css.rules.InlineRule
import com.greenmist.vector.svgkit.css.rules.SelectorRule
import com.greenmist.vector.svgkit.css.selectors.ClassSelector
import com.greenmist.vector.svgkit.css.selectors.NamedElementSelector
import com.greenmist.vector.svgkit.css.selectors.Selector
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class CascadeEngine {

    private var cascadeResult: CascadeResult? = null
    var matcherLists = Vector<MatcherList>()
    val classMap = Hashtable<String, Vector<MatcherRule>?>()
    val tagMap = Hashtable<String, Vector<MatcherRule>?>()
    var depth: Int = 0
    var order: Int = 0

    private fun collectSimpleSelectors(rule: SelectorRule, order: Int) {
        var list: Vector<MatcherRule>?
        var classSelector: ClassSelector
        var namedSelector: NamedElementSelector

        rule.selectors.forEach {
            if (isClassSelector(it)) {
                classSelector = it as ClassSelector
                list = classMap[classSelector.className]
                if (list == null) {
                    list = Vector()
                    classMap.put(classSelector.className, list)
                }
                list?.add(MatcherRule(classSelector.getElementMatcher(), rule, order))
            } else if (isTagSelector(it)) {
                namedSelector = it as NamedElementSelector
                list = tagMap[namedSelector.name]
                if (list == null) {
                    list = Vector()
                    tagMap.put(namedSelector.name, list)
                }
                list?.add(MatcherRule(namedSelector.getElementMatcher(), rule, order))
            }
        }
    }

    fun add(stylesheet: CssStylesheet, mediaList: Set<MatcherRule?>?) {
        val ml = MatcherList(stylesheet, mediaList)
        stylesheet.statements.forEach {
            if (it is SelectorRule) {
                if (mediaList == null) {
                    collectSimpleSelectors(it, order)
                }
                ml.addSelectorRule(it, depth, order++, mediaList != null)
            }
        }
        matcherLists.add(ml)
    }

    private fun applyRule(selector: Selector, order: Int, rule: BaseRule, pseudoElement: String?, mediaList: Set<MatcherRule?>?) {
        val specificity = selector.getSpecificity()
        applyRule(specificity, order, rule, pseudoElement, mediaList)
    }

    fun applyInlineRule(rule: InlineRule) {
        applyRule(0x7F000000, order, rule, null, null)
    }

    private fun applyRule(specificity: Int, order: Int, rule: BaseRule?, pseudoElement: String?, mediaList: Set<MatcherRule?>?) {
        if (rule?.properties == null) {
            return
        }

        rule.properties.forEach {
            val prop = it.key
            var importance = 0
            val value = it.value
            if (value is CssImportant) {
                importance = 1
            }
            var properties: ElementProperties?
            var style: InlineRule?
            mediaList?.forEach {
                properties = if (it == null) {
                    cascadeResult?.getProperties()
                } else {
                    cascadeResult?.getPropertiesForMedia(pseudoElement)
                }

                style = if (pseudoElement == null) {
                    properties?.getPropertySet()
                } else {
                    properties?.getPropertySetForPseudoElement(pseudoElement)
                }

                val existingCascadeValue = style?.get(prop) as CascadeValue
                val currentCascadeValue = CascadeValue(value, specificity, importance, order)
                if (currentCascadeValue.compareSpecificity(existingCascadeValue) > 0) {
                    style?.set(prop, currentCascadeValue)
                }
            }
        }
    }

    private fun applyClassRules(namespace: String?, name: String?, attrs: AttrMap?) {
        if (attrs != null) {
            val classAttr = ClassElementMatcher.getClassAttribute(namespace, name)
            if (classAttr != null) {
                val classStr = attrs.get("", classAttr)
                if (classStr != null) {
                    val tok = StringTokenizer(classStr.toString(), " ")
                    while (tok.hasMoreTokens()) {
                        val className = tok.nextToken()
                        classMap[className]?.forEach {
                            val (matcher, rule, order1) = it
                            if (matcher != null) {
                                applyRule(matcher.selector, order1, rule, null, null)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun applyTagRules(namespace: String?, name: String?) {
        tagMap[name]?.forEach {
            val (matcher, rule, order1) = it
            val s = matcher?.selector as NamedElementSelector
            if (!s.hasElementNamespace() || (namespace != null && s.namespace == namespace)) {
                applyRule(s, order1, rule, null, null)
            }
        }
    }

    fun pushElement(namespace: String?, name: String?, attrs: AttrMap?) {
        depth++
        cascadeResult = CascadeResult()
        applyTagRules(namespace, name)
        applyClassRules(namespace, name, attrs)
        matcherLists.forEach { matcherList ->
            matcherList.matchers.forEach { matcherRule ->
                val (matcher, rule, order1) = matcherRule
                val matcherResult = matcher?.pushElement(namespace, name, attrs)
                if (matcherResult != null) {
                    applyRule(matcher.selector, order1, rule, matcherResult.pseudoElement, matcherList.mediaList)
                }
            }
        }
    }

    fun popElement() {
        depth--
        matcherLists.forEach {
            it.matchers.forEach {
                it.matcher?.popElement()
            }
        }
    }

    internal fun makeRule(map: HashMap<String, CascadeValue>): BaseRule? {
        if (map.isEmpty()) {
            return null
        }

        val rule = InlineRule()
        map.forEach {
            rule.set(it.key, it.value)
        }
        return rule
    }

    fun getCascadeResult(): CascadeResult {
        val newCascadeResult = CascadeResult()
        val mediaList = cascadeResult?.media()
        val elementProperties = cascadeResult?.getProperties()

        mediaList?.forEach { media ->
            val mediaProps = cascadeResult?.getPropertiesForMedia(media)

            mediaProps?.pseudoElements()?.forEach { pseudoElement ->
                val propertySetMediaProps = mediaProps.getPropertySetForPseudoElement(pseudoElement)
                val elementProps = elementProperties?.getPropertySetForPseudoElement(pseudoElement)

                propertySetMediaProps.properties.forEach { propertyEntry ->
                        val generic = elementProps?.get(propertyEntry.key) as CascadeValue
                        val mediaSpecific = propertyEntry.value as CascadeValue
                        if (mediaSpecific.compareSpecificity(generic) > 0) {
                            val newCascadePropertiedForMedia = newCascadeResult.getPropertiesForMedia(media)
                            newCascadePropertiedForMedia.getPropertySetForPseudoElement(pseudoElement).set(propertyEntry.key, mediaSpecific)
                        }
                }
            }

            val mediaPropertySet = mediaProps?.getPropertySet()
            val elementPropertySet = elementProperties?.getPropertySet()

            mediaPropertySet?.properties()?.forEach {  property ->
                val mediaSpecific = mediaPropertySet.get(property) as CascadeValue
                val generic = elementPropertySet?.get(property) as CascadeValue
                if (mediaSpecific.compareSpecificity(generic) > 0) {
                    val newCascadePropertiedForMedia = newCascadeResult.getPropertiesForMedia(media)
                    newCascadePropertiedForMedia.getPropertySet().set(property, mediaSpecific)
                }
            }
        }


        elementProperties?.pseudoElements()?.forEach { pseudoElement ->
            val propertySetForPseudoElement = elementProperties.getPropertySetForPseudoElement(pseudoElement)
            propertySetForPseudoElement.properties.forEach { propertyEntry ->
                val cascadeValue = propertySetForPseudoElement.get(propertyEntry.key) as CascadeValue
                val newCasecadeProperties = newCascadeResult.getProperties()
                newCasecadeProperties.getPropertySetForPseudoElement(pseudoElement).set(propertyEntry.key, cascadeValue)
            }
        }

        val elementPropertySet = elementProperties?.getPropertySet()
        elementPropertySet?.properties?.forEach { propertyEntry ->
            val cascadeValue = elementPropertySet.get(propertyEntry.key) as CascadeValue
            val newCasecadeProperties = newCascadeResult.getProperties()
            newCasecadeProperties.getPropertySet().set(propertyEntry.key, cascadeValue)
        }
        return newCascadeResult
    }

    class MatcherList(val stylesheet: CssStylesheet, val mediaList: Set<MatcherRule?>?) {
        var matchers = Vector<MatcherRule>()

        fun addSelectorRule(rule: SelectorRule, depth: Int, order: Int, addSimpleSelectors: Boolean) {
            rule.selectors.forEach {
                var currentDepth = depth
                if (addSimpleSelectors || !(isClassSelector(it) || isTagSelector(it))) {
                    val matcher = it.getElementMatcher()
                    while (currentDepth > 0) {
                        matcher?.pushElement(null, "*", null)
                        currentDepth--
                    }
                    matchers.add(MatcherRule(matcher, rule, order))
                }
            }
        }
    }

    data class MatcherRule(var matcher: ElementMatcher?, var rule: SelectorRule, var order: Int)

    companion object {
        fun isClassSelector(s: Selector): Boolean = s is ClassSelector

        fun isTagSelector(s: Selector): Boolean = s is NamedElementSelector
    }
}