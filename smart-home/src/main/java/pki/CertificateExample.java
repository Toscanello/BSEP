package pki;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import pki.certificates.CertificateGenerator;
import pki.data.IssuerData;
import pki.data.SubjectData;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CertificateExample {
    public CertificateExample() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void testIt() {
        try {
            SubjectData subjectData = generateSubjectData();

            KeyPair keyPairIssuer = generateKeyPair();
            IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());

            CertificateGenerator cg = new CertificateGenerator();
            X509Certificate cert = cg.generateCertificate(subjectData, issuerData);

            System.out.println("\n===== Podaci o izdavacu sertifikata =====");
            System.out.println(cert.getIssuerX500Principal().getName());
            System.out.println("\n===== Podaci o vlasniku sertifikata =====");
            System.out.println(cert.getSubjectX500Principal().getName());
            System.out.println("\n===== Sertifikat =====");
            System.out.println("-------------------------------------------------------");
            System.out.println(cert);
            System.out.println("-------------------------------------------------------");

            cert.verify(keyPairIssuer.getPublic());
            System.out.println("\nValidacija uspesna :)");

            KeyPair anotherPair = generateKeyPair();
            cert.verify(anotherPair.getPublic());
        } catch (CertificateException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
            e.printStackTrace();
        }
    }

    private IssuerData generateIssuerData(PrivateKey issuerKey) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Goran Sladic");
        builder.addRDN(BCStyle.SURNAME, "Sladic");
        builder.addRDN(BCStyle.GIVENNAME, "Goran");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");

        builder.addRDN(BCStyle.UID, "123456");

        return new IssuerData(issuerKey, builder.build());
    }

    private SubjectData generateSubjectData() {
        try {
            KeyPair keyPairSubject = generateKeyPair();

            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse("2022-12-31");
            Date endDate = iso8601Formater.parse("2025-12-31");

            String sn = "1";

            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, "Marija Kovacevic");
            builder.addRDN(BCStyle.SURNAME, "Kovacevic");
            builder.addRDN(BCStyle.GIVENNAME, "Marija");
            builder.addRDN(BCStyle.O, "UNS-FTN");
            builder.addRDN(BCStyle.OU, "Katedra za informatiku");
            builder.addRDN(BCStyle.C, "RS");
            builder.addRDN(BCStyle.E, "marija.kovacevic@uns.ac.rs");
            builder.addRDN(BCStyle.UID, "654321");

            return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CertificateExample certificateExample = new CertificateExample();
        certificateExample.testIt();
    }
}