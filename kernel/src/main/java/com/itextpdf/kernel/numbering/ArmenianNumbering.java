/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.kernel.numbering;

/**
 * This class can produce String combinations representing an armenian numeral.
 * See https://en.wikipedia.org/wiki/Armenian_numerals
 */
public class ArmenianNumbering {

    private static final ArmenianDigit[] DIGITS = {
            new ArmenianDigit('\u0531', 1),
            new ArmenianDigit('\u0532', 2),
            new ArmenianDigit('\u0533', 3),
            new ArmenianDigit('\u0534', 4),
            new ArmenianDigit('\u0535', 5),
            new ArmenianDigit('\u0536', 6),
            new ArmenianDigit('\u0537', 7),
            new ArmenianDigit('\u0538', 8),
            new ArmenianDigit('\u0539', 9),
            new ArmenianDigit('\u053A', 10),
            new ArmenianDigit('\u053B', 20),
            new ArmenianDigit('\u053C', 30),
            new ArmenianDigit('\u053D', 40),
            new ArmenianDigit('\u053E', 50),
            new ArmenianDigit('\u053F', 60),
            new ArmenianDigit('\u0540', 70),
            new ArmenianDigit('\u0541', 80),
            new ArmenianDigit('\u0542', 90),
            new ArmenianDigit('\u0543', 100),
            new ArmenianDigit('\u0544', 200),
            new ArmenianDigit('\u0545', 300),
            new ArmenianDigit('\u0546', 400),
            new ArmenianDigit('\u0547', 500),
            new ArmenianDigit('\u0548', 600),
            new ArmenianDigit('\u0549', 700),
            new ArmenianDigit('\u054A', 800),
            new ArmenianDigit('\u054B', 900),
            new ArmenianDigit('\u054C', 1000),
            new ArmenianDigit('\u054D', 2000),
            new ArmenianDigit('\u054E', 3000),
            new ArmenianDigit('\u054F', 4000),
            new ArmenianDigit('\u0550', 5000),
            new ArmenianDigit('\u0551', 6000),
            new ArmenianDigit('\u0552', 7000),
            new ArmenianDigit('\u0553', 8000),
            new ArmenianDigit('\u0554', 9000)
    };

    private ArmenianNumbering() {
    }

    /**
     * Converts an integer to armenian numeral representation.
     *
     * @param number a number greater than zero to be converted to armenian notation
     * @return an armenian numeral representation of an integer.
     */
    public static String toArmenian(int number) {
        StringBuilder result = new StringBuilder();
        for (int i = DIGITS.length - 1; i >= 0; i--) {
            ArmenianDigit curDigit = DIGITS[i];
            while (number >= curDigit.value) {
                result.append(curDigit.digit);
                number -= curDigit.value;
            }
        }
        return result.toString();
    }

    private static class ArmenianDigit {
        char digit;
        int value;

        ArmenianDigit(char digit, int value) {
            this.digit = digit;
            this.value = value;
        }
    }

}
