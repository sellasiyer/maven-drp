package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.cmp;
import static org.easymock.EasyMock.replay;

import org.easymock.EasyMock;
import org.easymock.LogicalOperator;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.HotlinkDao;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.drp.domain.HotlinkFactory;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link HotlinkServiceImpl}.
 */
public class HotlinkServiceImplTest {

    private HotlinkDao hotlinkDao;

    private HotlinkServiceImpl service = new HotlinkServiceImpl();

    private User testUser = new User();

    @Before
    public void setUp() {
	hotlinkDao = EasyMock.createStrictMock(HotlinkDao.class);
	service.setHotlinkDao(hotlinkDao);

	testUser.setUserId("a777");
    }

    @Test
    public void testUpdateLink() throws ServiceException, IseBusinessException, DataAccessException {
	Hotlink updatedHotlink = HotlinkFactory.getHotLink();
	updatedHotlink.setModifiedBy(testUser.getUserId());

	hotlinkDao.updateHotlink(cmp(updatedHotlink, new HotlinkComparator(), LogicalOperator.EQUAL));

	replay(hotlinkDao);

	Hotlink hl = new Hotlink();
	hl.setId(updatedHotlink.getId());
	hl.setUrlAlias(updatedHotlink.getUrlAlias());
	hl.setUrl(updatedHotlink.getUrl());
	hl.setUserId(updatedHotlink.getUserId());
	hl.setDisplayOrder(updatedHotlink.getDisplayOrder());
	service.createUpdateLink(hl, testUser);
    }

}
