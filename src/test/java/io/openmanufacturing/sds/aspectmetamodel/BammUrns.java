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

public class BammUrns {
   final String nameUrn;
   final String preferredNameUrn;
   final String descriptionUrn;
   final String propertiesUrn;
   final String operationsUrn;
   final String datatypeUrn;
   final String characteristicUrn;
   final String baseCharacteristicUrn;
   final String listTypeUrn;
   final String exampleValueUrn;
   final String valueUrn;
   final String inputUrn;
   final String seeUrn;
   final String leftUrn;
   final String rightUrn;
   final String defaultValueUrn;
   final String allowDuplicatesUrn;
   final String orderedUrn;
   final String unitUrn;
   final String minValueUrn;
   final String maxValueUrn;
   final String valuesUrn;
   final String languageCodeUrn;
   final String localeCodeUrn;
   final String lowerBoundDefinition;
   final String upperBoundDefinition;
   final String deconstructionRule;
   final String elements;
   final String list;
   final String structuredValue;
   final String scale;
   final String integer;
   final String extendsUrn;

   BammUrns( final KnownVersion testedBammVersion ) {
      final String META_META_MODEL_URN = "urn:bamm:io.openmanufacturing:meta-meta-model:%s#";
      final String META_MODEL_URN = "urn:bamm:io.openmanufacturing:meta-model:%s#";
      final String CHARACTERISTICS_URN = "urn:bamm:io.openmanufacturing:characteristic:%s#";

      listTypeUrn = String.format( META_META_MODEL_URN + "listType", testedBammVersion.toVersionString() );
      allowDuplicatesUrn = String
            .format( META_META_MODEL_URN + "allowDuplicates", testedBammVersion.toVersionString() );
      orderedUrn = String.format( META_META_MODEL_URN + "ordered", testedBammVersion.toVersionString() );

      nameUrn = String.format( META_MODEL_URN + "name", testedBammVersion.toVersionString() );
      preferredNameUrn = String.format( META_MODEL_URN + "preferredName", testedBammVersion.toVersionString() );
      descriptionUrn = String.format( META_MODEL_URN + "description", testedBammVersion.toVersionString() );
      propertiesUrn = String.format( META_MODEL_URN + "properties", testedBammVersion.toVersionString() );
      operationsUrn = String.format( META_MODEL_URN + "operations", testedBammVersion.toVersionString() );
      datatypeUrn = String.format( META_MODEL_URN + "dataType", testedBammVersion.toVersionString() );
      characteristicUrn = String.format( META_MODEL_URN + "characteristic", testedBammVersion.toVersionString() );
      exampleValueUrn = String.format( META_MODEL_URN + "exampleValue", testedBammVersion.toVersionString() );
      valueUrn = String.format( META_MODEL_URN + "value", testedBammVersion.toVersionString() );
      inputUrn = String.format( META_MODEL_URN + "input", testedBammVersion.toVersionString() );
      seeUrn = String.format( META_MODEL_URN + "see", testedBammVersion.toVersionString() );
      leftUrn = String.format( CHARACTERISTICS_URN + "left", testedBammVersion.toVersionString() );
      rightUrn = String.format( CHARACTERISTICS_URN + "right", testedBammVersion.toVersionString() );

      defaultValueUrn = String.format( CHARACTERISTICS_URN + "defaultValue", testedBammVersion.toVersionString() );
      unitUrn = String.format( CHARACTERISTICS_URN + "unit", testedBammVersion.toVersionString() );
      minValueUrn = String.format( CHARACTERISTICS_URN + "minValue", testedBammVersion.toVersionString() );
      maxValueUrn = String.format( CHARACTERISTICS_URN + "maxValue", testedBammVersion.toVersionString() );
      valuesUrn = String.format( CHARACTERISTICS_URN + "values", testedBammVersion.toVersionString() );
      lowerBoundDefinition = String
            .format( CHARACTERISTICS_URN + "lowerBoundDefinition", testedBammVersion.toVersionString() );
      upperBoundDefinition = String
            .format( CHARACTERISTICS_URN + "upperBoundDefinition", testedBammVersion.toVersionString() );
      deconstructionRule = String
            .format( CHARACTERISTICS_URN + "deconstructionRule", testedBammVersion.toVersionString() );
      elements = String.format( CHARACTERISTICS_URN + "elements", testedBammVersion.toVersionString() );
      list = String.format( CHARACTERISTICS_URN + "List", testedBammVersion.toVersionString() );
      structuredValue = String.format( CHARACTERISTICS_URN + "StructuredValue", testedBammVersion.toVersionString() );
      scale = String.format( CHARACTERISTICS_URN + "scale", testedBammVersion.toVersionString() );
      integer = String.format( CHARACTERISTICS_URN + "integer", testedBammVersion.toVersionString() );
      languageCodeUrn = String.format( CHARACTERISTICS_URN + "languageCode", testedBammVersion.toVersionString() );
      localeCodeUrn = String.format( CHARACTERISTICS_URN + "localeCode", testedBammVersion.toVersionString() );
      baseCharacteristicUrn = String
            .format( CHARACTERISTICS_URN + "baseCharacteristic", testedBammVersion.toVersionString() );
      extendsUrn = String.format( META_MODEL_URN + "extends", testedBammVersion.toVersionString() );
   }
}
