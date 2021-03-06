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

public class OperationShapeTest extends AbstractShapeTest {
   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testOperationValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "operation-shape", "TestOperation", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingRequiredPropertiesExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );

      final String operationName = "TestOperationMissingRequiredProperties";
      final String operationId = TEST_NAMESPACE_PREFIX + operationName;
      final SemanticError resultForName = new SemanticError( MESSAGE_MISSING_REQUIRED_PROPERTY,
            operationId, bammUrns.nameUrn, VIOLATION_URN, "" );
      final SemanticError resultForInput = new SemanticError(
            MESSAGE_MISSING_REQUIRED_PROPERTY, operationId, bammUrns.inputUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "operation-shape", operationName,
            metaModelVersion, resultForName, resultForInput );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEmptyPropertiesExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );

      final String aspectName = "TestOperationWithEmptyProperties";
      final String operationName = "TestOperation";
      final String operationId = TEST_NAMESPACE_PREFIX + operationName;
      final SemanticError resultForName = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            operationId, bammUrns.nameUrn, VIOLATION_URN, "" );
      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            operationId, bammUrns.preferredNameUrn, VIOLATION_URN, "@en" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            operationId, bammUrns.descriptionUrn, VIOLATION_URN, "@en" );
      expectSemanticValidationErrors( "operation-shape", aspectName,
            metaModelVersion, resultForName, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testLanguageStringNotUniqueExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );

      final String aspectName = "TestOperationNonUniqueLangStrings";
      final String operationName = "TestOperation";
      final String operationId = TEST_NAMESPACE_PREFIX + operationName;
      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            operationId, bammUrns.preferredNameUrn, VIOLATION_URN, "" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            operationId, bammUrns.descriptionUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "operation-shape", aspectName,
            metaModelVersion, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidLanguageStringsExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );

      final String aspectName = "TestOperationWithInvalidLangStrings";
      final String operationName = "TestOperation";
      final String operationId = TEST_NAMESPACE_PREFIX + operationName;
      final SemanticError resultForPreferredName = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, operationId, bammUrns.preferredNameUrn, VIOLATION_URN, "Test Operation" );
      final SemanticError resultForDescription = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, operationId, bammUrns.descriptionUrn, VIOLATION_URN, "Test Operation." );
      expectSemanticValidationErrors( "operation-shape", aspectName,
            metaModelVersion, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInputListContainsInvalidElementsExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "operation-shape", "TestOperationWithInvalidInput", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_VALUE_IS_NO_PROPERTY );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( TEST_NAMESPACE_PREFIX + "input" );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testOutputListContainsInvalidElementsExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "operation-shape", "TestOperationWithInvalidOutput", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_VALUE_IS_NO_PROPERTY );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( TEST_NAMESPACE_PREFIX + "output" );
   }
}
