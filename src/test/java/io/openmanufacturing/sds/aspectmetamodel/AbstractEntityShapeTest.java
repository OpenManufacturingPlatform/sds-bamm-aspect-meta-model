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

public class AbstractEntityShapeTest extends AbstractShapeTest {

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingAbstractEntityValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "abstract-entity-shape", "AbstractTestEntity", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingEntityValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "abstract-entity-shape", "ExtendedTestEntity", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testInstantiatingAbstractEntityExpectFailure( final KnownVersion metaModelVersion ) {
      final String focusNode = TEST_NAMESPACE_PREFIX + "AbstractEntityInstance";
      final String value = TEST_NAMESPACE_PREFIX + "InstantiatedAbstractTestEntity";

      final SemanticError resultForInstantiatedAbstractEntity = new SemanticError( MESSAGE_INSTANTIATED_ABSTRACT_ENTITY,
            focusNode, "", VIOLATION_URN, value );
      expectSemanticValidationErrors( "abstract-entity-shape", "InstantiatedAbstractTestEntity", metaModelVersion,
            resultForInstantiatedAbstractEntity );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testExtendingMultipleEntitiesExpectFailure( final KnownVersion metaModelVersion ) {
      final BammUrns bammUrns = new BammUrns( metaModelVersion );
      final String focusNode = TEST_NAMESPACE_PREFIX + "TestEntityExtendingMultipleEntities";

      final SemanticError resultForInstantiatedAbstractEntity = new SemanticError( MESSAGE_DUPLICATE_PROPERTY,
            focusNode, bammUrns.extendsUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "abstract-entity-shape", "TestEntityExtendingMultipleEntities", metaModelVersion,
            resultForInstantiatedAbstractEntity );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testAmbiguousEntitiesDueToPropertyNamesExpectFailure( final KnownVersion metaModelVersion ) {

      //test extending Entities use the same Property
      SemanticError resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name a which causes ambiguity in combination with the :B Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "a" );
      SemanticError resultForAmbiguousEntityB = new SemanticError( "The Entity :B contains a Property with name a which causes ambiguity in combination with the :A Entity.",
            TEST_NAMESPACE_PREFIX + "B", "", VIOLATION_URN, "a" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithExtendingEntitiesWithSameProperty",
            metaModelVersion, resultForAmbiguousEntityA, resultForAmbiguousEntityB );

      //test payload name in Abstract Entity is equal to Property name in extending Entity
      resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name abstractString which causes ambiguity in combination with the :AbstractTestEntityWithPayloadNameEqualToExtendingEntityProperty Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "abstractString" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithPayloadNameEqualToExtendingEntityProperty",
            metaModelVersion, resultForAmbiguousEntityA );

      //test extending Entities have Properties with the same payload name
      resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name same which causes ambiguity in combination with the :B Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "same" );
      resultForAmbiguousEntityB = new SemanticError( "The Entity :B contains a Property with name same which causes ambiguity in combination with the :A Entity.",
            TEST_NAMESPACE_PREFIX + "B", "", VIOLATION_URN, "same" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithExtendingEntitiesWithSamePayloadName",
            metaModelVersion, resultForAmbiguousEntityA, resultForAmbiguousEntityB );

      //test extending Entities have Properties with same name and payload name
      resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name b which causes ambiguity in combination with the :B Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "b" );
      resultForAmbiguousEntityB = new SemanticError( "The Entity :B contains a Property with name b which causes ambiguity in combination with the :A Entity.",
            TEST_NAMESPACE_PREFIX + "B", "", VIOLATION_URN, "b" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithExtendingEntitiesWithPayloadNameEqualToPropertyName",
            metaModelVersion, resultForAmbiguousEntityA, resultForAmbiguousEntityB );

      //test extending Entity with ambiguous Property in hierarchy
      final SemanticError resultForAmbiguousExtendingEntityLevelOne = new SemanticError(
            "The Entity :ExtendingEntityLevelOne contains a Property with name abstractTestProperty which causes ambiguity in combination with the :AbstractTestEntity Entity.",
            TEST_NAMESPACE_PREFIX + "ExtendingEntityLevelOne", "", VIOLATION_URN, "abstractTestProperty" );
      final SemanticError resultForAmbiguousExtendingEntityLevelTwo = new SemanticError(
            "The Entity :ExtendingEntityLevelTwo contains a Property with name testPropertyLevelOne which causes ambiguity in combination with the :ExtendingEntityLevelOne Entity.",
            TEST_NAMESPACE_PREFIX + "ExtendingEntityLevelTwo", "", VIOLATION_URN, "testPropertyLevelOne" );


      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithAmbiguousEntityInHierarchy",
            metaModelVersion, resultForAmbiguousExtendingEntityLevelOne, resultForAmbiguousExtendingEntityLevelTwo );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testAmbiguousEntitiesDueToOptionalPropertyCombinationsExpectFailure( final KnownVersion metaModelVersion ) {

      //test Abstract Entity has optional Property and extending Entity uses same Property without optional
      SemanticError resultForAmbiguousExtendingEntity = new SemanticError( "The Entity :ExtendingEntity contains a Property with name abstractOptionalProperty which causes ambiguity in combination with the :AbstractTestEntityWithOptionalPropertyAndExtendingEntityWithSameProperty Entity.",
            TEST_NAMESPACE_PREFIX + "ExtendingEntity", "", VIOLATION_URN, "abstractOptionalProperty" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithOptionalPropertyAndExtendingEntityWithSameProperty",
            metaModelVersion, resultForAmbiguousExtendingEntity );

      //test Abstract Entity has optional Property and extending Entity uses same Property with optional
      resultForAmbiguousExtendingEntity = new SemanticError( "The Entity :ExtendingEntity contains a Property with name abstractOptionalProperty which causes ambiguity in combination with the :AbstractTestEntityWithOptionalPropertyAndExtendingEntityWithSameProperty Entity.",
            TEST_NAMESPACE_PREFIX + "ExtendingEntity", "", VIOLATION_URN, "abstractOptionalProperty" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithOptionalPropertyAndExtendingEntityWithSameOptionalProperty",
            metaModelVersion, resultForAmbiguousExtendingEntity );

      //test extending Entities have the same optional Properties
      SemanticError resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name a which causes ambiguity in combination with the :B Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "a" );
      SemanticError resultForAmbiguousEntityB = new SemanticError( "The Entity :B contains a Property with name a which causes ambiguity in combination with the :A Entity.",
            TEST_NAMESPACE_PREFIX + "B", "", VIOLATION_URN, "a" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithExtendingEntitiesWithSameOptionalProperty",
            metaModelVersion, resultForAmbiguousEntityA, resultForAmbiguousEntityB );

      //test extending Entities have the same optional and Mandatory Property
      resultForAmbiguousEntityA = new SemanticError( "The Entity :A contains a Property with name b which causes ambiguity in combination with the :B Entity.",
            TEST_NAMESPACE_PREFIX + "A", "", VIOLATION_URN, "b" );
      resultForAmbiguousEntityB = new SemanticError( "The Entity :B contains a Property with name b which causes ambiguity in combination with the :A Entity.",
            TEST_NAMESPACE_PREFIX + "B", "", VIOLATION_URN, "b" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityWithExtendingEntitiesWithSameOptionalAndMandatoryProperty",
            metaModelVersion, resultForAmbiguousEntityA, resultForAmbiguousEntityB );
   }

   @ParameterizedTest
   @MethodSource( value = "versionsStartingWith2_0_0" )
   public void testAbstractEntityIsNotExtendedExpectFailure( final KnownVersion metaModelVersion ) {
      SemanticError result = new SemanticError( "The Abstract Entity :AbstractTestEntity which is used as the data-type for AbstractEntity has not been extended.",
            TEST_NAMESPACE_PREFIX + "AbstractTestEntity", "", VIOLATION_URN, TEST_NAMESPACE_PREFIX + "AbstractEntity" );
      expectSemanticValidationErrors( "abstract-entity-shape", "AbstractTestEntityIsNotExtended",
            metaModelVersion, result );
   }

}
