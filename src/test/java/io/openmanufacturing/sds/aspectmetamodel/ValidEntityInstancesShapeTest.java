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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.openmanufacturing.sds.validation.SemanticError;

public class ValidEntityInstancesShapeTest extends AbstractShapeTest {
   private final String FOCUS_NODE = TEST_NAMESPACE_PREFIX + "Instance";

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityInstanceExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "TestEntityInstance", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityInstanceExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "ExtendingEntityInstance", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingRequiredPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError resultForMissingProperty = new SemanticError(
            MESSAGE_INSTANCE_MISSING_REQUIRED_PROPERTY, FOCUS_NODE, "", VIOLATION_URN,
            TEST_NAMESPACE_PREFIX + "intProperty" );
      expectSemanticValidationErrors( "valid-entity-instance-shape",
            "TestEntityInstanceMissingRequiredProperties",
            metaModelVersion, resultForMissingProperty );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingOptionalPropertyExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "TestEntityInstanceMissingOptionalProperties", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidValueTypeExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError resultInvalidValueType = new SemanticError(
            MESSAGE_INSTANCE_INVALID_VALUE_TYPE,
            FOCUS_NODE, TEST_NAMESPACE_PREFIX + "intProperty", VIOLATION_URN, "bar" );
      expectSemanticValidationErrors( "valid-entity-instance-shape",
            "TestEntityInstanceInvalidValueType",
            metaModelVersion, resultInvalidValueType );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingNotInPayloadPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError validationError = getSingleSemanticValidationError(
            "valid-entity-instance-shape",
            "TestEntityInstanceMissingNotInPayloadProperties",
            metaModelVersion );

      assertThat( validationError.getResultMessage() ).isEqualTo( MESSAGE_INSTANCE_MISSING_REQUIRED_PROPERTY );
      assertThat( validationError.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( validationError.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityInstanceWithListExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "TestEntityWithListInstance", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithListInstanceWithoutListExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError validationError = getSingleSemanticValidationError(
            "valid-entity-instance-shape",
            "TestEntityWithListInstanceWithoutList",
            metaModelVersion );

      assertThat( validationError.getResultMessage() ).isEqualTo( MESSAGE_INSTANCE_INVALID_TYPE_FOR_LIST_PROPERTY );
      assertThat( validationError.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( validationError.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithListInstanceInvalidTypeInListExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError validationError = getSingleSemanticValidationError(
            "valid-entity-instance-shape",
            "TestEntityWithListInstanceInvalidTypeInList",
            metaModelVersion );

      assertThat( validationError.getResultMessage() ).isEqualTo( MESSAGE_INSTANCE_INVALID_TYPE_CONTAINED_IN_LIST );
      assertThat( validationError.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( validationError.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityInstanceWithEntityListExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "TestEntityWithEntityListInstance", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithEntityListInstanceInvalidTypeInListExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError validationError = getSingleSemanticValidationError(
            "valid-entity-instance-shape",
            "TestEntityWithEntityListInstanceInvalidTypeInList",
            metaModelVersion );

      assertThat( validationError.getResultMessage() ).isEqualTo( MESSAGE_INSTANCE_INVALID_TYPE_CONTAINED_IN_LIST );
      assertThat( validationError.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( validationError.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityInstanceMissingRequiredPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError resultForMissingProperty = new SemanticError( MESSAGE_INSTANCE_MISSING_REQUIRED_PROPERTY,
            TEST_NAMESPACE_PREFIX + "ExtendingEntityInstance", "", VIOLATION_URN,
            TEST_NAMESPACE_PREFIX + "abstractTestProperty" );
      expectSemanticValidationErrors( "valid-entity-instance-shape", "ExtendingEntityMissingRequiredProperty",
            metaModelVersion, resultForMissingProperty );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityInstanceMissingOptionalPropertyExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "valid-entity-instance-shape", "ExtendingEntityMissingOptionalProperty", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityInstanceInvalidValueType( final KnownVersion metaModelVersion ) {
      final SemanticError resultForMissingProperty = new SemanticError( MESSAGE_INSTANCE_INVALID_VALUE_TYPE,
            TEST_NAMESPACE_PREFIX + "ExtendingEntityInstance", TEST_NAMESPACE_PREFIX + "abstractTestProperty",
            VIOLATION_URN, "345" );
      expectSemanticValidationErrors( "valid-entity-instance-shape", "ExtendingEntityInstanceInvalidValueType",
            metaModelVersion, resultForMissingProperty );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityInstanceMissingNotInPayloadProperty( final KnownVersion metaModelVersion ) {
      final SemanticError validationError = getSingleSemanticValidationError(
            "valid-entity-instance-shape",
            "ExtendingEntityInstanceMissingNotInPayloadProperty",
            metaModelVersion );

      assertThat( validationError.getResultMessage() ).isEqualTo( MESSAGE_INSTANCE_MISSING_REQUIRED_PROPERTY );
      assertThat( validationError.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( validationError.getValue() ).isNotEmpty();
   }
}
