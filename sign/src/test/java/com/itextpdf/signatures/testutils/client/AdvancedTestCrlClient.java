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
package com.itextpdf.signatures.testutils.client;

import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.utils.DateTimeUtil;
import com.itextpdf.signatures.CrlClientOnline;
import com.itextpdf.signatures.testutils.TimeTestUtil;
import com.itextpdf.signatures.testutils.builder.TestCrlBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdvancedTestCrlClient extends CrlClientOnline {
    private final Map<String, TestCrlBuilder> crlBuilders = new LinkedHashMap<>();

    public AdvancedTestCrlClient addBuilderForCertIssuer(X509Certificate cert, TestCrlBuilder crlBuilder) {
        crlBuilders.put(cert.getSerialNumber().toString(16), crlBuilder);
        return this;
    }

    public AdvancedTestCrlClient addBuilderForCertIssuer(X509Certificate cert, X509Certificate issuerCert, PrivateKey issuerPrivateKey)
            throws CertificateEncodingException, IOException {
        Date yesterday = DateTimeUtil.addDaysToDate(TimeTestUtil.TEST_DATE_TIME, -1);
        crlBuilders.put(cert.getSerialNumber().toString(16), new TestCrlBuilder(issuerCert, issuerPrivateKey, yesterday));
        return this;
    }

    @Override
    protected InputStream getCrlResponse(X509Certificate cert, URL urlt) throws IOException {
        TestCrlBuilder builder = crlBuilders.get(cert.getSerialNumber().toString(16));
        try {
            return new ByteArrayInputStream(builder.makeCrl());
        } catch (AbstractOperatorCreationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
