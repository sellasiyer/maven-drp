package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Interface defining calls made to the Geek Squad Armory.
 */
public interface GeekSquadArmorySao {

    List<Article> getArticles(String searchText, String model, Carrier carrier) throws ServiceException,
	    IseBusinessException;

}
