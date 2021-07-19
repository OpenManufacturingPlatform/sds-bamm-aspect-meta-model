/*
  Copyright (c) 2021 Robert Bosch Manufacturing Solutions GmbH

  See the AUTHORS file(s) distributed with this work for additional
  information regarding authorship.

  This Source Code Form is subject to the terms of the Mozilla Public
  License, v. 2.0. If a copy of the MPL was not distributed with this
  file, You can obtain one at https://mozilla.org/MPL/2.0/.

 SPDX-License-Identifier: MPL-2.0
*/

/**
 * Checks if a given value is a regular expression.
 *
 * @param $this The context of the Constraint, i.e. the focus node
 * @param $value The value that can be reached from the focus node following the Shape's sh:path
 */
function isValidRegularExpression($this, $value) {
    if (!$value.isLiteral()) {
        return false;
    }

    var isValid = true;
    try {
        new RegExp($value.lex);
    } catch (e) {
        isValid = false;
    }

    return isValid;
}

function testStructuredValueConstruction($this) {
    var bamm = "urn:bamm:io.openmanufacturing:meta-model:2.0.0#";
    var bammc = "urn:bamm:io.openmanufacturing:characteristic:2.0.0#";
    var rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    var exampleValueUrn = TermFactory.namedNode(bamm + "exampleValue");
    var deconstructionRuleUrn = TermFactory.namedNode(bammc + "deconstructionRule");
    var elementsUrn = TermFactory.namedNode(bammc + "elements");
    var rdfRest = TermFactory.namedNode(rdf + "rest");
    var rdfFirst = TermFactory.namedNode(rdf + "first");
    var rdfNil = TermFactory.namedNode(rdf + "nil");

    var deconstructionRuleIterator = $data.find($this, deconstructionRuleUrn, null);
    var deconstructionRule = null;
    for (var it = deconstructionRuleIterator.next(); it; it = deconstructionRuleIterator.next()) {
        deconstructionRule = it.object.lex;
    }
    deconstructionRuleIterator.close();

    if (deconstructionRule == null) {
        return null;
    }

    var listNodeIterator = $data.find($this, elementsUrn, null);
    var listNode = listNodeIterator.next().object;
    var stringToMatch = "";
    var propertyValues = [];
    while (listNode != null && !("" + listNode === "" + rdfNil)) {
        var listElementIterator = $data.find(listNode, rdfFirst, null);
        var listElement = listElementIterator.next().object;
        if (!listElement.isURI() && !listElement.isLiteral()) {
            listElementIterator.close();
            return true;
        }
        var targetLiteral;
        if (listElement.isLiteral()) {
            targetLiteral = listElement.lex;
        } else {
            var exampleValueIterator = $data.find(listElement, exampleValueUrn, null);
            var exampleValue = exampleValueIterator.next();
            if (exampleValue == null) {
                // If one of the exampleValues is missing, skip additional validation
                exampleValueIterator.close();
                listElementIterator.close();
                return true;
            }
            targetLiteral = exampleValue.object.lex;
            exampleValueIterator.close();
            propertyValues.push(targetLiteral);
        }
        listElementIterator.close();
        stringToMatch += targetLiteral;
        listNodeIterator.close();
        listNodeIterator = $data.find(listNode, rdfRest, null);
        listNode = listNodeIterator.next().object;
    }
    listNodeIterator.close();

    var regex = new RegExp(deconstructionRule, 'g');
    var match = regex.exec(stringToMatch);

    if (match == null) {
        return {message: "Deconstruction rule did not match Properties in elements", value: deconstructionRule};
    }

    if (match.length - 1 != propertyValues.length) {
        return {
            message: "Number of matching capture groups (" + (match.length - 1) + ") in deconstructionRule does not match number of Properties in elements (" + propertyValues.length + ")",
            value: deconstructionRule
        };
    }

    for (var group = 1; group <= propertyValues.length; group++) {
        if (match[group] !== propertyValues[group - 1]) {
            return {
                message: "Given Property exampleValue (" + propertyValues[group - 1] + ") does not match group " + group + " from deconstructionRule (" + match[group] + ")",
                value: deconstructionRule
            };

        }
    }

    return true;
}
