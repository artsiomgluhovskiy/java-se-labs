package org.art.java_core.geolocation;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class GeolocationTest {

    private static final Logger log = LoggerFactory.getLogger(GeolocationTest.class);

    private static final String DB_LOCATION = "geoip/GeoLite2-Country.mmdb";

    public static void main(String[] args) throws URISyntaxException {
        String ip = "46.53.244.173";
        String countryCode = defineCountryByIp(ip);
        log.info("IP: {}, country code: {}", ip, countryCode);
    }

    private static String defineCountryByIp(String ip) throws URISyntaxException {
        String countryCode = StringUtils.EMPTY;
        URL dbURL = GeolocationTest.class.getClassLoader().getResource(DB_LOCATION);
        File db = Paths.get(dbURL.toURI()).toFile();
        try (DatabaseReader dbReader = new DatabaseReader.Builder(db).build()) {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = dbReader.country(ipAddress);
            countryCode = countryResponse.getCountry().getIsoCode();
        } catch (IOException e) {
            log.error("IOException has been caught.", e);
        } catch (GeoIp2Exception e) {
            log.error("GeoIp2Exception has been caught.", e);
        }
        return countryCode;
    }
}
