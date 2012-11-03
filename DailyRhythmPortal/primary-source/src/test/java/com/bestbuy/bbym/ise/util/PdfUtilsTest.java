package com.bestbuy.bbym.ise.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.bestbuy.bbym.ise.util.PDFUtils;
import com.bestbuy.bbym.ise.util.PDFUtils.ImageInfo;

/**
 * JUnit test for {@link PDFUtils}.
 */
public class PdfUtilsTest {

    /**
     * Test for populatePdfTemplateWithValues with tablet recommendation sheet
     * PDF.
     */
    @Test
    public void testPopulateTabletPdfTemplateWithValues() throws Exception {

	URL fileUrl = getClass().getClassLoader().getResource("English Tablet.pdf");

	Map<String, String> fieldNameValueMap = new HashMap<String, String>();

	// Page 1
	populatePage1(fieldNameValueMap);

	// Page 2
	// Walk out working
	fieldNameValueMap.put("walk_out_working_user_setup", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_applications", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_email", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_updates", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_sku", "5611796");

	fieldNameValueMap.put("step_1_tech_support", String.valueOf(true));
	fieldNameValueMap.put("step_1_tech_support_1year", String.valueOf(true));
	fieldNameValueMap.put("step_1_tech_support_2years", String.valueOf(true));
	fieldNameValueMap.put("step_1_tech_support_3years", String.valueOf(true));

	fieldNameValueMap.put("step_1_protection", String.valueOf(true));
	fieldNameValueMap.put("step_1_protection_1year", String.valueOf(true));
	fieldNameValueMap.put("step_1_protection_pricing_1year", "$29.99-$139.99");
	fieldNameValueMap.put("step_1_protection_2years", String.valueOf(true));
	fieldNameValueMap.put("step_1_protection_pricing_2years", "$59.99-$209.99");
	fieldNameValueMap.put("step_1_protection_3years", String.valueOf(true));
	fieldNameValueMap.put("step_1_protection_pricing_3years", "$89.99-$399.99");

	fieldNameValueMap.put("bundle_double_coverage", String.valueOf(true));
	fieldNameValueMap.put("bundle_1year", String.valueOf(true));
	fieldNameValueMap.put("bundle_pricing_1year", "$129.99");
	fieldNameValueMap.put("bundle_savings_1year", "$129.98");
	fieldNameValueMap.put("bundle_2years", String.valueOf(true));
	fieldNameValueMap.put("bundle_pricing_2years", "$229.99");
	fieldNameValueMap.put("bundle_savings_2years", "$149.98");

	fieldNameValueMap.put("step_2_locked_and_found", String.valueOf(true));
	fieldNameValueMap.put("step_2_locked_and_found_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_locked_and_found_pricing_1year", "$29.99");
	fieldNameValueMap.put("step_2_locked_and_found_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_locked_and_found_pricing_2years", "$39.99");
	fieldNameValueMap.put("step_2_locked_and_found_sku", "657804 + TERM");

	fieldNameValueMap.put("step_2_shield_install", String.valueOf(true));
	fieldNameValueMap.put("step_2_shield_install_pricing", "$14.99");
	fieldNameValueMap.put("step_2_shield_install_sku", "1414789");

	fieldNameValueMap.put("step_2_network_setup", String.valueOf(true));
	fieldNameValueMap.put("step_2_network_setup_pricing", "$129.99");
	fieldNameValueMap.put("step_2_network_setup_sku", "1015382");

	// byte[] imageBytes = FileUtils.readFileToByteArray(new File("C:/barcode.png"));
	List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
	// imageInfos.add(new ImageInfo(2, imageBytes, 505, 622, 75, 39));
	byte[] pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, imageInfos);

	// Use this for manual testing to see what the PDF really looks like
	// FileUtils.writeByteArrayToFile(new File("C:/Neville.pdf"), pdf);
    }

    /**
     * Test for populatePdfTemplateWithValues with Mac recommendation sheet PDF.
     */
    @Test
    public void testPopulateMacPdfTemplateWithValues() throws Exception {

	URL fileUrl = getClass().getClassLoader().getResource("English Mac.pdf");

	Map<String, String> fieldNameValueMap = new HashMap<String, String>();

	// Page 1
	populatePage1(fieldNameValueMap);

	// Page 2
	// Walk out working
	fieldNameValueMap.put("walk_out_working_user_setup", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_security_software", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_email", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_updates", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_sku", "5611878");

	fieldNameValueMap.put("step_1_internet_security_6months", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_1year", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_2years", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_3years", String.valueOf(true));
	fieldNameValueMap.put("step_1_webroot", String.valueOf(true));
	fieldNameValueMap.put("step_1_kaspersky", String.valueOf(true));
	fieldNameValueMap.put("step_1_titanium", String.valueOf(true));

	fieldNameValueMap.put("step_2_tech_support", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_protection", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_1year", "$139.99-$149.99");
	fieldNameValueMap.put("step_2_laptop_damage_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_2years", "$319.99-$349.99");
	fieldNameValueMap.put("step_2_laptop_damage_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_3years", "$219.99-$449.99");
	fieldNameValueMap.put("step_2_desktop_damage_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_1year", "$89.99-$169.99");
	fieldNameValueMap.put("step_2_desktop_damage_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_2years", "$129.99-$219.99");
	fieldNameValueMap.put("step_2_desktop_damage_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_3years", "$169.99-$269.99");

	fieldNameValueMap.put("bundle_double_coverage", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_1year", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_pricing_1year", "$239.99");
	fieldNameValueMap.put("bundle_laptop_savings_1year", "$139.98");
	fieldNameValueMap.put("bundle_laptop_2years", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_pricing_2years", "$399.99");
	fieldNameValueMap.put("bundle_laptop_savings_2years", "$249.98");
	fieldNameValueMap.put("bundle_desktop_1year", String.valueOf(true));
	fieldNameValueMap.put("bundle_desktop_pricing_1year", "$199.99");
	fieldNameValueMap.put("bundle_desktop_savings_1year", "$149.98");
	fieldNameValueMap.put("bundle_desktop_2years", String.valueOf(true));
	fieldNameValueMap.put("bundle_desktop_pricing_2years", "$329.99");
	fieldNameValueMap.put("bundle_desktop_savings_2years", "$169.98");

	fieldNameValueMap.put("step_3_data_backup", String.valueOf(true));
	fieldNameValueMap.put("step_3_data_backup_sku", "9244711");
	fieldNameValueMap.put("step_3_data_backup_pricing", "$80");
	fieldNameValueMap.put("step_3_data_backup_savings", "$19.99");
	fieldNameValueMap.put("step_3_data_backup_500_sku", "9244757");
	fieldNameValueMap.put("step_3_data_backup_500_pricing", "$130");
	fieldNameValueMap.put("step_3_data_backup_500_savings", "$19.99");

	fieldNameValueMap.put("step_3_office", String.valueOf(true));
	fieldNameValueMap.put("step_3_office_pricing", "$119.99");
	fieldNameValueMap.put("step_3_office_savings", "$20");
	fieldNameValueMap.put("step_3_office_sku", "1303083");

	fieldNameValueMap.put("step_3_apple_tv", String.valueOf(true));
	fieldNameValueMap.put("step_3_apple_tv_pricing", "$99.99");
	fieldNameValueMap.put("step_3_apple_tv_sku", "1331156");

	fieldNameValueMap.put("step_3_parallels", String.valueOf(true));
	fieldNameValueMap.put("step_3_parallels_sku", "6577374");
	fieldNameValueMap.put("step_3_parallels_pricing", "$50");
	fieldNameValueMap.put("step_3_parallels_savings", "$19.99");

	// byte[] imageBytes = FileUtils.readFileToByteArray(new File("C:/barcode.png"));
	List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
	// imageInfos.add(new ImageInfo(2, imageBytes, 505, 631, 90, 31));
	byte[] pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, imageInfos);

	// Use this for manual testing to see what the PDF really looks like
	// FileUtils.writeByteArrayToFile(new File("C:/Neville.pdf"), pdf);
    }

    /**
     * Test for populatePdfTemplateWithValues with PC recommendation sheet PDF.
     */
    @Test
    public void testPopulatePcPdfTemplateWithValues() throws Exception {

	URL fileUrl = getClass().getClassLoader().getResource("English PC.pdf");

	Map<String, String> fieldNameValueMap = new HashMap<String, String>();

	// Page 1
	populatePage1(fieldNameValueMap);

	// Page 2
	// Walk out working
	fieldNameValueMap.put("walk_out_working_user_setup", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_security_software", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_email", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_updates", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_sku", "5611878");

	fieldNameValueMap.put("step_1_internet_security_6months", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_1year", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_2years", String.valueOf(true));
	fieldNameValueMap.put("step_1_internet_security_3years", String.valueOf(true));
	fieldNameValueMap.put("step_1_webroot", String.valueOf(true));
	fieldNameValueMap.put("step_1_kaspersky", String.valueOf(true));
	fieldNameValueMap.put("step_1_titanium", String.valueOf(true));

	fieldNameValueMap.put("step_2_tech_support", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_tech_support_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_protection", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_1year", "$59.99-$149.99");
	fieldNameValueMap.put("step_2_laptop_damage_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_2years", "$129.99-$349.99");
	fieldNameValueMap.put("step_2_laptop_damage_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_laptop_damage_pricing_3years", "$219.99-$449.99");
	fieldNameValueMap.put("step_2_desktop_damage_1year", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_1year", "$39.99-$169.99");
	fieldNameValueMap.put("step_2_desktop_damage_2years", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_2years", "$59.99-$219.99");
	fieldNameValueMap.put("step_2_desktop_damage_3years", String.valueOf(true));
	fieldNameValueMap.put("step_2_desktop_damage_pricing_3years", "$79.99-$269.99");

	fieldNameValueMap.put("bundle_double_coverage", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_1year", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_pricing_1year", "$169.99");
	fieldNameValueMap.put("bundle_laptop_savings_1year", "$139.98");
	fieldNameValueMap.put("bundle_laptop_2years", String.valueOf(true));
	fieldNameValueMap.put("bundle_laptop_pricing_2years", "$279.99");
	fieldNameValueMap.put("bundle_laptop_savings_2years", "$209.98");
	fieldNameValueMap.put("bundle_desktop_1year", String.valueOf(true));
	fieldNameValueMap.put("bundle_desktop_pricing_1year", "$139.99");
	fieldNameValueMap.put("bundle_desktop_savings_1year", "$149.98");
	fieldNameValueMap.put("bundle_desktop_2years", String.valueOf(true));
	fieldNameValueMap.put("bundle_desktop_pricing_2years", "$249.99");
	fieldNameValueMap.put("bundle_desktop_savings_2years", "$169.98");

	fieldNameValueMap.put("step_3_data_backup", String.valueOf(true));
	fieldNameValueMap.put("step_3_data_backup_sku", "9244711");
	fieldNameValueMap.put("step_3_data_backup_pricing", "$80");
	fieldNameValueMap.put("step_3_data_backup_savings", "$19.99");
	fieldNameValueMap.put("step_3_data_backup_500_sku", "9244757");
	fieldNameValueMap.put("step_3_data_backup_500_pricing", "$130");
	fieldNameValueMap.put("step_3_data_backup_500_savings", "$19.99");

	fieldNameValueMap.put("step_3_office", String.valueOf(true));
	fieldNameValueMap.put("step_3_office_sku", "1303056/1478804");
	fieldNameValueMap.put("step_3_office_pricing", "$99.99");
	fieldNameValueMap.put("step_3_office_savings", "$49.99");

	fieldNameValueMap.put("step_3_restore_media", String.valueOf(true));
	fieldNameValueMap.put("step_3_restore_sku", "7243716");
	fieldNameValueMap.put("step_3_restore_pricing", "$40");
	fieldNameValueMap.put("step_3_restore_savings", "$19.99");

	fieldNameValueMap.put("step_3_pc_setup", String.valueOf(true));
	fieldNameValueMap.put("step_3_pc_setup_sku", "8789297/7243716");
	fieldNameValueMap.put("step_3_pc_setup_pricing", "$80");
	fieldNameValueMap.put("step_3_pc_setup_savings", "$29.98");

	// byte[] imageBytes = FileUtils.readFileToByteArray(new File("C:/barcode.png"));
	List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
	// imageInfos.add(new ImageInfo(2, imageBytes, 489, 631, 90, 31));
	byte[] pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, imageInfos);

	// Use this for manual testing to see what the PDF really looks like
	// FileUtils.writeByteArrayToFile(new File("C:/Neville.pdf"), pdf);
    }

    /**
     * Test for populatePdfTemplateWithValues with Mobile recommendation sheet PDF.
     */
    @Test
    public void testPopulateMobilePdfTemplateWithValues() throws Exception {

	URL fileUrl = getClass().getClassLoader().getResource("Mobile_Rec_Sheet.pdf");

	Map<String, String> fieldNameValueMap = new HashMap<String, String>();

	// Page 1
	fieldNameValueMap.put("you_name", "FirstName LastName");
	fieldNameValueMap.put("you_phone", "(555) 123-4567");
	fieldNameValueMap.put("you_upgrade_date", "1/11/14");
	fieldNameValueMap.put("you_text", String.valueOf(true));
	fieldNameValueMap.put("you_call", String.valueOf(true));
	fieldNameValueMap.put("you_current", "My current connectivity plan, features, etc.");
	fieldNameValueMap.put("trade_in_value", "$100.23");

	fieldNameValueMap.put("perfect_device_internet", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_email", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_music", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_video", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_photo", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_tv", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_navigation", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_gaming", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_texting", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_unlocked", String.valueOf(true));
	fieldNameValueMap.put("perfect_device_do", "make toast");

	fieldNameValueMap.put("how_do_you_use_the_internet", "How I use the internet");
	fieldNameValueMap.put("date", "5/6/17");

	// Recommendations
	fieldNameValueMap.put("recommendations_plan_and_features",
		"recommended plan, recommended feature 1, recommended feature 2, recommended useage");
	fieldNameValueMap.put("recommendations_phone", "Recommended phone or device");

	// Walk out working
	fieldNameValueMap.put("walk_out_working_data_transfer", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_social", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_email", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_power", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_bluetooth", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_voicemail", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_applications", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_other", String.valueOf(true));
	fieldNameValueMap.put("walk_out_working_software_updates", String.valueOf(true));

	// Essentials
	fieldNameValueMap.put("essentials_hands_free", "essentials hands free");
	fieldNameValueMap.put("essentials_memory", "essentials memory");
	fieldNameValueMap.put("essentials_case_shield", "essentials case shield");
	fieldNameValueMap.put("essentials_appcessories", "essentials appcessories");
	fieldNameValueMap.put("essentials_charger", "essentials charger");
	fieldNameValueMap.put("essentials_geek_squad_protection", "essentials geek squad protection");
	fieldNameValueMap.put("essentials_trade_in_plus", "essentials trade in plus");
	fieldNameValueMap.put("essentials_financing", "essentials financing");

	fieldNameValueMap.put("notes",
		"note 1, note 2, note 3, note 4, note 5, note 6, note 7, note 8, note 9, note 10");
	fieldNameValueMap.put("specialist_contact_info", "Name \nOther stuff");

	byte[] pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, null);

	// Use this for manual testing to see what the PDF really looks like
	//FileUtils.writeByteArrayToFile(new File("C:/Neville.pdf"), pdf);
    }

    /**
     * All fields on page 1 have the same values
     */
    private void populatePage1(Map<String, String> fieldNameValueMap) {

	// You
	fieldNameValueMap.put("you_name", "FirstName LastName");
	fieldNameValueMap.put("you_address", "123 1st Ave, minneapolis, MN 55416");
	fieldNameValueMap.put("you_phone", "(555) 123-4567");
	fieldNameValueMap.put("you_date", "11/12/13");
	fieldNameValueMap.put("you_email", "john.smith@email.com");
	fieldNameValueMap.put("you_upgrade_date_month", "1");
	fieldNameValueMap.put("you_upgrade_date_day", "11");
	fieldNameValueMap.put("you_upgrade_date_year", "14");
	fieldNameValueMap.put("you_text", String.valueOf(true));
	fieldNameValueMap.put("you_call", String.valueOf(true));

	// Right harware
	fieldNameValueMap.put("perfect_device_do", "make toast");
	fieldNameValueMap.put("seen_and_like", "seen it all");
	fieldNameValueMap.put("looking_for", "looking for the answer");
	fieldNameValueMap.put("important_to_you", "important stuff");
	fieldNameValueMap.put("where_primary_use", "wherever I am");
	fieldNameValueMap.put("programs_use_most", "My favorite programs");

	// Center column
	fieldNameValueMap.put("how_where_connect_to_internet", "At home, at work, at play");
	fieldNameValueMap.put("other_devices_to_connect", "Toasters, fridge, TV");
	fieldNameValueMap.put("existing_hardware", "Old phone\nold PC");
	fieldNameValueMap.put("trade_in_value", "$100.23");
	fieldNameValueMap
		.put("recommendations",
			"recommendation 1, recommendation 2, recommendation 3, recommendation 4, recommendation 5, recommendation 6");
	fieldNameValueMap.put("notes",
		"note 1, note 2, note 3, note 4, note 5, note 6, note 7, note 8, note 9, note 10");

	fieldNameValueMap.put("date", "5/6/17");

	// Essentials
	fieldNameValueMap.put("essentials_heard_of_geek_squad", "essentials heard");
	fieldNameValueMap.put("essentials_geek_squad_protection", "essentials protection");
	fieldNameValueMap.put("essentials_geek_squad_services", "essentials services");
	fieldNameValueMap.put("essentials_internet_security", "essentials security");
	fieldNameValueMap.put("essentials_microsoft_office", "essentials microsoft office");
	fieldNameValueMap.put("essentials_printer_office_supplies", "essentials supplies");
	fieldNameValueMap.put("essentials_accessories", "essentials accessories");
	fieldNameValueMap.put("essentials_networking", "essentials networking");
	fieldNameValueMap.put("essentials_external_storage", "essentials storage");
	fieldNameValueMap.put("essentials_financing_and_rewards", "essentials financing");
	fieldNameValueMap.put("essentials_headphones_speakers", "essentials speakers");

	// Consultant info
	fieldNameValueMap.put("consultant_info_name", "Super Consultant");
	fieldNameValueMap.put("consultant_info_employee_number", "a12345678");
	fieldNameValueMap.put("consultant_info_store_phone_number", "(999) 123-4567");
    }

    /**
     * Test for populatePdfTemplateWithValues with Services Bar Codes PDF.
     */
    @Test
    public void testPopulateServicesBarCodesPdfTemplate() throws Exception {

	URL fileUrl = getClass().getClassLoader().getResource("Services_Bar_codes.pdf");

	Map<String, String> fieldNameValueMap = new HashMap<String, String>();

	fieldNameValueMap.put("customer", "Some customer");
	fieldNameValueMap.put("description1", "1. Geek Squad Protection 1 Year(s)");
	fieldNameValueMap.put("description2", "2. Geek Squad Protection 2 Year(s)");
	fieldNameValueMap.put("description3", "3. Geek Squad Protection 3 Year(s)");
	fieldNameValueMap.put("description4", "4. Geek Squad Protection 4 Year(s)");
	fieldNameValueMap.put("description5", "5. Geek Squad Protection 5 Year(s)");
	fieldNameValueMap.put("description6", "6. Geek Squad Protection 6 Year(s)");
	fieldNameValueMap.put("description7", "7. Geek Squad Protection 7 Year(s)");
	fieldNameValueMap.put("description8", "8. Geek Squad Protection 8 Year(s)");

	// byte[] imageBytes = FileUtils.readFileToByteArray(new File("C:/barcode.png"));
	List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
	int y = 555;
	int lineSpacing = 63;
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	// imageInfos.add(new ImageInfo(1, imageBytes, 320, y -= lineSpacing, 75, 39));
	byte[] pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, imageInfos);

	// Use this for manual testing to see what the PDF really looks like
	// FileUtils.writeByteArrayToFile(new File("C:/Neville.pdf"), pdf);
    }
}
