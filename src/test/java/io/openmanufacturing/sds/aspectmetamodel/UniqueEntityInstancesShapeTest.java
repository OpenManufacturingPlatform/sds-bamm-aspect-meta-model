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

public class UniqueEntityInstancesShapeTest extends AbstractShapeTest {

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testIdenticalInstancesExpectFailure( final KnownVersion metaModelVersion ) {
      final String focusNode = TEST_NAMESPACE_PREFIX + "Instance";
      final SemanticError resultForName = new SemanticError( MESSAGE_IDENTICAL_INSTANCES,
            focusNode, "", WARNING_URN, focusNode + "2" );
      expectSemanticValidationErrors( "unique-entity-instance-shape",
            "TestEntityInstanceIdenticalInstances",
            metaModelVersion, resultForName );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInstancesWithSomeIdenticalValuesExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "unique-entity-instance-shape", "TestEntityInstanceSomeIdenticalValues", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testIdenticalExtendingEntityInstancesExpectFailure( final KnownVersion metaModelVersion ) {
      final String focusNode = TEST_NAMESPACE_PREFIX + "ExtendingEntityInstance";
      final SemanticError resultForIdenticalInstance = new SemanticError( MESSAGE_IDENTICAL_INSTANCES, focusNode, "",
            WARNING_URN, TEST_NAMESPACE_PREFIX + "ExtendingEntityInstanceTwo" );
      expectSemanticValidationErrors( "unique-entity-instance-shape", "ExtendingEntityIdenticalInstances",
            metaModelVersion, resultForIdenticalInstance );
   }
}
