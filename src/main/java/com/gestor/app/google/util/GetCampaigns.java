package com.gestor.app.google.util;

import java.io.File;
import java.io.IOException;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v14.errors.GoogleAdsError;
import com.google.ads.googleads.v14.errors.GoogleAdsException;
import com.google.ads.googleads.v14.services.GoogleAdsRow;
import com.google.ads.googleads.v14.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v14.services.SearchGoogleAdsStreamRequest;
import com.google.ads.googleads.v14.services.SearchGoogleAdsStreamResponse;
import com.google.api.ads.common.lib.utils.examples.CodeSampleParams;
import com.google.api.gax.rpc.ServerStream;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;

/** Gets all campaigns. To add campaigns, run AddCampaigns.java. */
public class GetCampaigns {

	private static class GetCampaignsWithStatsParams extends CodeSampleParams {

//    @Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId = 5293682072L;
	}

	public static void main(String[] args) throws IOException {
		GetCampaignsWithStatsParams params = new GetCampaignsWithStatsParams();
		if (!params.parseArguments(args)) {

			// Either pass the required parameters for this example on the command line, or
			// insert them
			// into the code here. See the parameter class definition above for
			// descriptions.
			params.customerId = 3613787017L;
		}

		GoogleAdsClient googleAdsClient = null;
		ClassLoader classLoader = GetCampaigns.class.getClassLoader();
		File configFile = new File(classLoader.getResource("ads.properties").getFile());

//    	
//      googleAdsClient = GoogleAdsClient.newBuilder().fromPropertiesFile(configFile)
//    		  .build();

		Credentials credentials = UserCredentials.newBuilder()
				.setClientId("828712689621-nffg53p4uh7n0k6iup6s5h9idaqu1fqc.apps.googleusercontent.com")
				.setClientSecret("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")
				.setRefreshToken(
						"1//0hGLJ17bEQWRlCgYIARAAGBESNwF-L9IrBxFipFToepzQGMA2Pf1n2csFPtRKYvVI7AMDGa8hGQHM_RQGZgixIUWHlq5aqhcU5X0")
				.build();

		googleAdsClient = GoogleAdsClient.newBuilder().setDeveloperToken("Q1hZH-tvLYoWbY-5DQt3iw")
				.setLoginCustomerId(7792900240L).setCredentials(credentials).build();

		try {
			new GetCampaigns().runExample(googleAdsClient, params.customerId);
		} catch (GoogleAdsException gae) {
			// GoogleAdsException is the base class for most exceptions thrown by an API
			// request.
			// Instances of this exception have a message and a GoogleAdsFailure that
			// contains a
			// collection of GoogleAdsErrors that indicate the underlying causes of the
			// GoogleAdsException.
			System.err.printf("Request ID %s failed due to GoogleAdsException. Underlying errors:%n",
					gae.getRequestId());
			int i = 0;
			for (GoogleAdsError googleAdsError : gae.getGoogleAdsFailure().getErrorsList()) {
				System.err.printf("  Error %d: %s%n", i++, googleAdsError);
			}
			System.exit(1);
		}
	}

	/**
	 * Runs the example.
	 *
	 * @param googleAdsClient the Google Ads API client.
	 * @param customerId      the client customer ID.
	 * @throws GoogleAdsException if an API request failed with one or more service
	 *                            errors.
	 */
  private void runExample(GoogleAdsClient googleAdsClient, long customerId) {
    try (GoogleAdsServiceClient googleAdsServiceClient =
        googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
      String query = "SELECT "
      		+ "campaign.id, "
      		+ "campaign.name, "
      		+ "campaign.status, "
      		+ "metrics.impressions, "
      		+ "metrics.absolute_top_impression_percentage, "
      		+ "metrics.top_impression_percentage, "
      		+ "metrics.invalid_clicks, "
      		+ "metrics.average_cpc, "
      		+ "metrics.cost_micros, "
      		+ "metrics.ctr, "
      		+ "metrics.phone_calls, "
      		+ "metrics.phone_impressions, "
      		+ "metrics.clicks, "
      		+ "metrics.cost_micros, "
      		+ "metrics.cost_per_conversion "
      		+ "FROM campaign "
      		+ "WHERE segments.date DURING TODAY";
      // Constructs the SearchGoogleAdsStreamRequest.
      SearchGoogleAdsStreamRequest request =
          SearchGoogleAdsStreamRequest.newBuilder()
              .setCustomerId(Long.toString(customerId))
              .setQuery(query)
              .build();

      // Creates and issues a search Google Ads stream request that will retrieve all campaigns.
      ServerStream<SearchGoogleAdsStreamResponse> stream =
          googleAdsServiceClient.searchStreamCallable().call(request);

      Long invalidClicks = 0L;
      Long clicks = 0L;
      Long impression = 0L;
      Long costMicros = 0L;
      Long cpcMicros = 0L;
      int totalRows = 0;
      Double averageCpc = 0.0;
      Double averageCost = 0.0;
      Double ctr = 0.0;
      // Iterates through and prints all of the results in the stream response.
      for (SearchGoogleAdsStreamResponse response : stream) {
    	  totalRows =  response.getResultsList().size();
        for (GoogleAdsRow googleAdsRow : response.getResultsList()) {
          System.out.printf(
              "Campaign with ID %d and name '%s' was found.%n",
              googleAdsRow.getCampaign().getId() ,googleAdsRow.getCampaign().getName());
          googleAdsRow.getCampaign().getStatus();
          googleAdsRow.getMetrics();
          
          invalidClicks = invalidClicks + googleAdsRow.getMetrics().getInvalidClicks();
          clicks = clicks + googleAdsRow.getMetrics().getClicks();
          impression = impression + googleAdsRow.getMetrics().getImpressions();
          costMicros = costMicros + googleAdsRow.getMetrics().getCostMicros();
          averageCpc = averageCpc +  googleAdsRow.getMetrics().getAverageCpc();
          averageCost = averageCost +  googleAdsRow.getMetrics().getAverageCost();
          ctr = ctr + googleAdsRow.getMetrics().getCtr();
          
        }
      }
      invalidClicks.toString(); 
      clicks.toString(); // acesso ao site
      impression.toString(); // quantidade de anuncios
      Double costMicrosTotal = (double) costMicros / 1_000_000; costMicrosTotal.toString(); // custo total
      averageCpc.toString();
      averageCost = averageCost / 1_000_000; averageCost.toString();
      
      Double totalCpc = (double) costMicros / clicks / 1_000_000; // CUSTO POR CLIQUE (CPC)
      totalCpc.toString();
      
      ctr = ctr*100/totalRows; // não funciona
      ctr.toString();
    }
  }


}