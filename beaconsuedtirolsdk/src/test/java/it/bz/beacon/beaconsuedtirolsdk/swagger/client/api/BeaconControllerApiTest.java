/*
 * Beacon Suedtirol API TEST
 * The API for the Beacon Suedtirol project for configuring beacons and accessing beacon data.
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package it.bz.beacon.beaconsuedtirolsdk.swagger.client.api;

import it.bz.beacon.beaconsuedtirolsdk.swagger.client.ApiException;
import it.bz.beacon.beaconsuedtirolsdk.swagger.client.model.Beacon;
import it.bz.beacon.beaconsuedtirolsdk.swagger.client.model.BeaconUpdate;
import it.bz.beacon.beaconsuedtirolsdk.swagger.client.model.ManufacturerOrder;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for BeaconControllerApi
 */
@Ignore
public class BeaconControllerApiTest {

    private final BeaconControllerApi api = new BeaconControllerApi();

    
    /**
     * Create a beacon
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createUsingPOSTTest() throws ApiException {
        ManufacturerOrder order = null;
        List<Beacon> response = api.createUsingPOST(order);

        // TODO: test validations
    }
    
    /**
     * View a list of available beacons
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getListUsingGETTest() throws ApiException {
        List<Beacon> response = api.getListUsingGET();

        // TODO: test validations
    }
    
    /**
     * Search a beacon with an ID
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getUsingGETTest() throws ApiException {
        String id = null;
        Beacon response = api.getUsingGET(id);

        // TODO: test validations
    }
    
    /**
     * Update a beacon
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateUsingPATCHTest() throws ApiException {
        BeaconUpdate beaconUpdate = null;
        String id = null;
        Beacon response = api.updateUsingPATCH(beaconUpdate, id);

        // TODO: test validations
    }
    
}
