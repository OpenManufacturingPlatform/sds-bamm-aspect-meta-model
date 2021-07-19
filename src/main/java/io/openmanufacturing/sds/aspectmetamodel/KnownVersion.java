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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The known versions of the Aspect Meta Model
 */
public enum KnownVersion {
   BAMM_1_0_0,
   BAMM_2_0_0;

   /**
    * Returns this version as a standard version string, e.g. 1.2.3
    */
   public String toVersionString() {
      return toString().replaceFirst( "BAMM_(\\d+)_(\\d+)_(\\d+)", "$1.$2.$3" );
   }

   public static Optional<KnownVersion> fromVersionString( final String version ) {
      return Arrays.stream( KnownVersion.values() )
                   .filter( value -> value.toVersionString().equals( version ) )
                   .findAny();
   }

   public static KnownVersion getLatest() {
      return Arrays.asList( KnownVersion.values() ).get( KnownVersion.values().length - 1 );
   }

   public static List<KnownVersion> getVersions() {
      return Arrays.asList( values() );
   }

   public boolean isNewerThan( final KnownVersion otherVersion ) {
      return toVersionString().compareTo( otherVersion.toVersionString() ) > 0;
   }
}
