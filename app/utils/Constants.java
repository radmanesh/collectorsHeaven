/*
 * (C) Copyright 2018 arman (collectors) and others.
 *
 * Constants.java
 * Created 9:44:39 AM
 */
package utils;

import models.users.UserRole;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 *
 * @author arman
 * @version 1
 * @date Mar 8, 2018
 */
public final class Constants {

    /** The Constant defaultEmail. */
    public static final String defaultEmail = "noreply@collectorsheaven.com";
    
    /** The Constant defaultNumberOfItemsToShow. */
    public static final int defaultNumberOfItemsToShow = 5;
    
    /** Default number of similar properties shown in pages. */
    public static final int defaultNumberOfSimilarItemsToShow = 5;

    /** The Constant defaultUserRoleName. */
    public static final String defaultUserRoleName = "user";
    
    /** The Constant defaultCollectorRoleName. */
    public static final String defaultCollectorRoleName = "collector";
    
    /** The Constant defaultGuestUserRoleName. */
    public static final String defaultGuestUserRoleName = "guest";
    
    /** The Constant defaultAdminRoleName. */
    public static final String defaultAdminRoleName = "admin";

    /** The Constant defaultUserRole. */
    public static final UserRole defaultUserRole = UserRole.getOrCreate(defaultUserRoleName);
    
    /** The Constant defaultCollectorRole. */
    public static final UserRole defaultCollectorRole = UserRole.getOrCreate(defaultCollectorRoleName);
    
    /** The Constant defaultAdminRole. */
    public static final UserRole defaultAdminRole = UserRole.getOrCreate(defaultAdminRoleName);

    /** The Constant itemNoImagePath. */
    public static final String itemNoImagePath = "/public/img/collection/item-noImage.jpg";

    /** The Constant collectionNoIconImagePath. */
    public static final String collectionNoIconImagePath = "/public/img/collection/collection-noIcon.jpg";
    
    /** The Constant collectionNoBannerImagePath. */
    public static final String collectionNoBannerImagePath = "/public/img/collection/collection-noBanner.jpg";

    /** The Constant collectorNoBannerImagePath. */
    public static final String collectorNoBannerImagePath = "/public/img/collection/collector/collector-noBanner.jpg";
    
    /** The Constant collectorNoIconImagePath. */
    public static final String collectorNoIconImagePath = "/public/img/collection/collector/collector-noIcon.jpg";

    /** The Constant bannerNoImagePath. */
    public static final String bannerNoImagePath = "/public/img/banner/noBanner.jpg";

    /** The Constant defaultUserLang. */
    public static final String defaultUserLang = "fa";
    
    /** The Constant defaultAppLang. */
    public static final String defaultAppLang = "fa";

    /** The Constant DEFAULT_APP_LANG_COOKIE. */
    public static final String DEFAULT_APP_LANG_COOKIE = "BM_LANG";

}
