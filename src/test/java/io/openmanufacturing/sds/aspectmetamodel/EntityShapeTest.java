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

public class EntityShapeTest extends AbstractShapeTest {
   private final String SECOND_TEST_ENTITY = TEST_NAMESPACE_PREFIX + "SecondTestEntity";

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "TestEntity", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityValidationWithRefinedEntityExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "RefinedTestEntity", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testMissingRequiredPropertiesExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestEntityMissingRequiredProperties";

      final SemanticError resultForName = new SemanticError( MESSAGE_MISSING_REQUIRED_PROPERTY,
            focusNode, bammUrns.nameUrn, VIOLATION_URN, "" );
      final SemanticError resultForProperties = new SemanticError(
            MESSAGE_MISSING_REQUIRED_PROPERTY, focusNode, bammUrns.propertiesUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "entity-shape", "TestEntityMissingRequiredProperties", metaModelVersion,
            resultForName, resultForProperties );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEmptyPropertiesExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestEntityWithEmptyProperties";

      final SemanticError resultForName = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            focusNode, bammUrns.nameUrn, VIOLATION_URN, "" );
      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            focusNode, bammUrns.preferredNameUrn, VIOLATION_URN, "@en" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            focusNode, bammUrns.descriptionUrn, VIOLATION_URN, "@en" );
      expectSemanticValidationErrors( "entity-shape", "TestEntityWithEmptyProperties",
            metaModelVersion, resultForName, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testLanguageStringNotUniqueExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestEntityNonUniqueLangStrings";

      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            focusNode, bammUrns.preferredNameUrn, VIOLATION_URN, "" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            focusNode, bammUrns.descriptionUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "entity-shape", "TestEntityNonUniqueLangStrings", metaModelVersion,
            resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidLanguageStringsExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestEntityWithInvalidLangStrings";

      final SemanticError resultForPreferredName = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, focusNode, bammUrns.preferredNameUrn, VIOLATION_URN, "Test Entity" );
      final SemanticError resultForDescription = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, focusNode, bammUrns.descriptionUrn, VIOLATION_URN, "A test Entity" );
      expectSemanticValidationErrors( "entity-shape", "TestEntityWithInvalidLangStrings", metaModelVersion,
            resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testPropertyListContainsInvalidElementsExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithInvalidProperties", metaModelVersion );
      assertThat( result.getResultMessage() )
            .satisfies( s -> assertThat( s ).isEqualTo( MESSAGE_INVALID_ENTRY_ENTITY_PROPERTY_LIST ) );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( SECOND_TEST_ENTITY );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsUpToIncluding1_0_0" )
   public void testEntityRefiningEntityDeclaresAdditionalPropertiesExpectFailure(
         final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityRefiningEntityDeclaresAdditionalProperties", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_NO_ADDITIONAL_PROPERTIES );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( "" );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithOptionalPropertyExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "TestEntityWithOptionalProperty", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithInvalidOptionalPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithInvalidOptionalProperty", metaModelVersion );
      assertThat( result.getResultMessage() )
            .satisfies( s -> assertThat( s ).isEqualTo( MESSAGE_INVALID_ENTRY_ENTITY_PROPERTY_LIST ) );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithMissingOptionalPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithMissingOptionalProperty", metaModelVersion );
      assertThat( result.getResultMessage() )
            .satisfies( s -> assertThat( s ).isEqualTo( MESSAGE_INVALID_ENTRY_ENTITY_PROPERTY_LIST ) );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithNotInPayloadPropertyExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "TestEntityWithNotInPayloadProperty", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithNotInPayloadAndOptionalPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithNotInPayloadAndOptionalProperty", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_INVALID_ENTITY_PROPERTY_DEFINITION );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( "" );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithInvalidNotInPayloadPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithInvalidNotInPayloadProperty", metaModelVersion );
      assertThat( result.getResultMessage() )
            .satisfies( s -> assertThat( s ).isEqualTo( MESSAGE_INVALID_ENTRY_ENTITY_PROPERTY_LIST ) );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithOnlyNotInPayloadPropertyExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithSingleNotInPayloadProperty", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_INVALID_ENTITY_WITH_SINGLE_PROPERTY );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( "" );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithNotInPayloadPropertyNotUsedInEnumerationExpectFailure(
         final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithNotInPayloadPropertyWithoutEnumeration", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_ENTITY_NOT_USED_IN_ENUMERATION );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isNotEmpty();
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testNestedEntityWithNotInPayloadPropertyExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "NestedEntityWithNotInPayloadProperty", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithPropertyAndPayloadNameExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "TestEntityWithPropertyAndPayloadName", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithOptionalPropertyAndPayloadNameExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "entity-shape", "TestEntityWithOptionalPropertyAndPayloadName", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEntityWithNotInPayloadPropertyAndPayloadNameExpectFailure( final KnownVersion metaModelVersion ) {
      final SemanticError result = getSingleSemanticValidationError(
            "entity-shape", "TestEntityWithNotInPayloadPropertyAndPayloadName", metaModelVersion );
      assertThat( result.getResultMessage() ).isEqualTo( MESSAGE_INVALID_ENTITY_PROPERTY_PAYLOAD_NAME );
      assertThat( result.getResultSeverity() ).isEqualTo( VIOLATION_URN );
      assertThat( result.getValue() ).isEqualTo( "" );
   }
}
