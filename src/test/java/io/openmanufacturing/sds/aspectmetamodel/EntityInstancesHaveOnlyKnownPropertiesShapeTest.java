/*
 * Copyright (c) 2021 Robert Bosch Manufacturing Solutions GmbH
 *
 * See the AUTHORS file(s) distributed with this work for additional
 * information regarding authorship.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package io.openmanufacturing.sds.aspectmetamodel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.openmanufacturing.sds.validation.SemanticError;

public class EntityInstancesHaveOnlyKnownPropertiesShapeTest extends AbstractShapeTest {

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testAdditionalUnknownPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final String focusNode = TEST_NAMESPACE_PREFIX + "Instance";
      final SemanticError resultForName = new SemanticError(
            MESSAGE_INSTANCE_UNKNOWN_PROPERTY, focusNode, "", WARNING_URN, TEST_NAMESPACE_PREFIX + "intProperty" );
      expectSemanticValidationErrors( "entities-have-only-known-properties-shape",
            "TestEntityInstanceWithUnknownProperties", metaModelVersion, resultForName );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testAdditionalUnknownPropertyInExtendingEntityInstanceExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError resultForName = new SemanticError(
            MESSAGE_INSTANCE_UNKNOWN_PROPERTY, TEST_NAMESPACE_PREFIX + "ExtendingEntityInstance", "",
            WARNING_URN, TEST_NAMESPACE_PREFIX + "unkown" );
      expectSemanticValidationErrors( "entities-have-only-known-properties-shape",
            "ExtendingEntityInstanceWithUnknownProperties", metaModelVersion, resultForName );
   }
}
