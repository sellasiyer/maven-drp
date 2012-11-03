package com.bestbuy.bbym.ise.drp.domain;

public enum RecommendationTypeEnum {
    MOBILE("mobile", 1), PC("pc", 2), MAC("mac", 3), TABLET("tablet", 4);

    private final String id;
    private final int uniq;

    private RecommendationTypeEnum(String id, int uniq) {
	this.id = id;
	this.uniq = uniq;
    }

    public static int getUniqById(String vid) {
	RecommendationTypeEnum[] types = RecommendationTypeEnum.values();
	for(RecommendationTypeEnum current: types){
	    if (current.getId().equalsIgnoreCase(vid)){
		return current.getUniq();
	    }
	}
	return -1;
    }

    public static String getIdByUniq(int vuniq) {
	RecommendationTypeEnum[] types = RecommendationTypeEnum.values();
	for(RecommendationTypeEnum current: types){
	    if (current.getUniq() == vuniq){
		return current.getId();
	    }
	}
	return null;
    }

    public String getId() {
	return id;
    }

    public int getUniq() {
	return uniq;
    }

}
