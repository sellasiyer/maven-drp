package com.bestbuy.bbym.ise.drp.helpers;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * 
 * @author a904002
 */
public class MyNamespacePrefixMapper extends NamespacePrefixMapper {

    private String namespace;

    public MyNamespacePrefixMapper(String namespace) {
	this.namespace = namespace;
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

	return namespace;

    }
}
