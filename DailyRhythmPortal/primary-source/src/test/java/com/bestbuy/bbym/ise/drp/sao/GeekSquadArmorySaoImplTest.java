package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Article;

/**
 * JUnit test for {@link GeekSquadArmorySaoImpl}.
 */
public class GeekSquadArmorySaoImplTest extends BaseSaoTest {

    private GeekSquadArmorySaoImpl geekSquadArmorySaoImpl;
    private RestOperations mockRestOperations;

    @Override
    public void setUp() {
	super.setUp();

	mockRestOperations = EasyMock.createMock(RestOperations.class);
	geekSquadArmorySaoImpl = new GeekSquadArmorySaoImpl();
	ReflectionTestUtils.setField(geekSquadArmorySaoImpl, "restOperations", mockRestOperations);

	geekSquadArmorySaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for Geek Squad Armory service?
    }

    /**
     * Test for {@link GeekSquadArmorySaoImpl#getArticles(String, String, Carrier)}.
     */
    @Test
    public void testGetArticles() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<Class<Article[]>> captureResponseType = new Capture<Class<Article[]>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	Article[] responseArticles = loadJsonResponse("GeekSquad_ArmorySearch.json", Article[].class);

	final String url = "someArmoryUrl";
	final String armorySearchSuffix = "someArmorySearchSuffix";

	EasyMock.expect(drpPropertyService.getProperty("GEEKSQUAD_ARMORY_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("GEEKSQUAD_ARMORY_SEARCH_SUFFIX")).andReturn(armorySearchSuffix);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.getForObject(capture(captureUrl), capture(captureResponseType),
			capture(captureUriVariables))).andReturn(responseArticles);
	EasyMock.replay(mockRestOperations);

	final String searchText = "someSearchText";
	final String model = "someModel";
	final Carrier carrier = Carrier.VERIZON;

	List<Article> articles = geekSquadArmorySaoImpl.getArticles(searchText, model, carrier);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + armorySearchSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", Article[].class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (text) passed in service call", searchText, captureUriVariables.getValue()
		.get("text"));
	assertEquals("Incorrect uriVariable (model) passed in service call", model, captureUriVariables.getValue().get(
		"model"));
	assertEquals("Incorrect uriVariable (carrier) passed in service call", carrier.getLabel(), captureUriVariables
		.getValue().get("carrier"));

	// Check that correct values were returned from SAO
	assertNotNull("No articles returned from service call", articles);
	assertEquals("Incorrect number of articles returned from service call", 5, articles.size());
	Article article = articles.get(0);
	assertEquals("Incorrect article title", "iPhone home button not working", article.getTitle());
	assertEquals(
		"Incorrect article link",
		"http://geeksquadlabs.com/armory/passthru.php?articleId=7&t=1347999608&sig=bb92576d47eaf4d7b7a84e55145792cb",
		article.getLink());
	assertEquals("Incorrect article atricleAbstract", "Some Abstract", article.getArticleAbstract());
	// TODO Check the contents of the remaining articles!
    }
}
