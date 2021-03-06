package oracle.sysman.emaas.platform.emcpdf.tenant.subscription2;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import mockit.*;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.metadata.ApplicationEditionConverter;
import oracle.sysman.emaas.platform.emcpdf.cache.api.ICacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.support.CacheManagers;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.DefaultKeyGenerator;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Keys;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Tenant;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheConstants;
import oracle.sysman.emaas.platform.emcpdf.cache.util.StringUtil;
import oracle.sysman.emaas.platform.emcpdf.rc.RestClient;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil.VersionedLink;
import oracle.sysman.emaas.platform.emcpdf.tenant.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.emcpdf.tenant.lookup.RetryableLookupClient;
import oracle.sysman.emaas.platform.emcpdf.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author guobaochen
 */
public class TenantSubscriptionUtilTest {
    private static final String ENTITY_NAMING_DOMAINS_URL = "http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains";
    private static final String TENANT_LOOKUP_URL = "http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/58bfe535-7134-4cae-be4a-d6d3dcfdb4d8/lookups?opcTenantId=emaastesttenant1";

    // @formatter:off
    private static final String ENTITY_NAMING_DOMAIN = "{"
            + "\"total\": 12,"
            + "\"items\": ["
            + "        {"
            + "            \"uuid\": \"58bfe535-7134-4cae-be4a-d6d3dcfdb4d8\","
            + "            \"domainName\": \"TenantApplicationMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/58bfe535-7134-4cae-be4a-d6d3dcfdb4d8\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"opcTenantId\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"4136781e-d91c-4d69-976e-d7c298c4aca2\","
            + "            \"domainName\": \"EmlacoreTenantDBLogin\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/4136781e-d91c-4d69-976e-d7c298c4aca2\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantid\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"9c3a576f-736e-4d23-b106-e26d4bce8adf\","
            + "            \"domainName\": \"TenantSchemaMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/9c3a576f-736e-4d23-b106-e26d4bce8adf\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantID\""
            + "                },"
            + "                {"
            + "                    \"name\": \"serviceName\""
            + "                },"
            + "                {"
            + "                    \"name\": \"serviceVersion\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"f4de97e5-47b4-4d43-ad33-322429897f1b\","
            + "            \"domainName\": \"CloudDatabaseResources\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/f4de97e5-47b4-4d43-ad33-322429897f1b\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"id\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"915c5cad-a175-4303-9c00-f47bd670cd78\","
            + "            \"domainName\": \"LogAnalyticsSolrOSSResources\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/915c5cad-a175-4303-9c00-f47bd670cd78\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"credentials\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"98d39323-cf21-42e3-ada5-bd1e6247e587\","
            + "            \"domainName\": \"DataServiceDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/98d39323-cf21-42e3-ada5-bd1e6247e587\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"PropertyBag\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"82af926b-6502-49f7-a12d-3d917d6320cc\","
            + "            \"domainName\": \"HAHDFSDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/82af926b-6502-49f7-a12d-3d917d6320cc\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"NameNode\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"9423b075-9a12-4293-bccb-85541df80652\","
            + "            \"domainName\": \"ITADatabaseTenantMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/9423b075-9a12-4293-bccb-85541df80652\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantid\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"7d10280e-c174-48cf-b911-acce1c539daa\","
            + "            \"domainName\": \"zkBackupUtilityDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/7d10280e-c174-48cf-b911-acce1c539daa\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"zookeeperCluster\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"86a7a003-ce0a-4b1a-8fc4-aa2496b6189f\","
            + "            \"domainName\": \"InternalTenantIdMap\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/86a7a003-ce0a-4b1a-8fc4-aa2496b6189f\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"opcTenantId\""
            + "                },"
            + "                {"
            + "                    \"name\": \"internalTenantId\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"2fbb32ea-aece-4fe3-b6df-430bd16a877f\","
            + "            \"domainName\": \"ApplicationShardTenantMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/2fbb32ea-aece-4fe3-b6df-430bd16a877f\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"applicationShard\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"b1f390b3-c2e1-4c1b-9977-5cf133e44a47\","
            + "            \"domainName\": \"HAHDFSClusterDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/b1f390b3-c2e1-4c1b-9977-5cf133e44a47\","
            + "            \"keys\": [" + "                {" + "                    \"name\": \"NodeFqhn\""
            + "                }" + "            ]" + "        }" + "    ]," + "    \"count\": 12" + "}";

    // @formatter:off
    private final static String TENANT_LOOKUP_RESULT = "{"
            + "    \"total\": 1,"
            + "    \"items\": ["
            + "        {"
            + "            \"domainUuid\": \"58bfe535-7134-4cae-be4a-d6d3dcfdb4d8\","
            + "            \"domainName\": \"TenantApplicationMapping\","
            + "            \"uuid\": \"ea395485-3981-4fc3-94c0-900523f3ebd0\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/58bfe535-7134-4cae-be4a-d6d3dcfdb4d8/lookups/ea395485-3981-4fc3-94c0-900523f3ebd0\","
            + "            \"keys\": [" + "                {" + "                    \"name\": \"opcTenantId\","
            + "                    \"value\": \"emaastesttenant1\"" + "                }" + "            ],"
            + "            \"values\": [" + "                {" + "                    \"opcTenantId\": \"emaastesttenant1\","
            + "                    \"applicationNames\": \"APM,LogAnalytics,ITAnalytics\"" + "                }"
            + "            ]," + "            \"hash\": -2083815310" + "        }" + "    ]," + "    \"count\": 1" + "}";
    // @formatter:on
    // @formatter:off
    private static final String ENTITY_NAMING_DOMAIN_EMPTY_TENANT_APP_URL = "{"
            + "\"total\": 12,"
            + "\"items\": ["
            + "        {"
            + "            \"uuid\": \"58bfe535-7134-4cae-be4a-d6d3dcfdb4d8\","
            + "            \"domainName\": \"TenantApplicationMapping\","
            + "            \"canonicalUrl\": \"\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"opcTenantId\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"4136781e-d91c-4d69-976e-d7c298c4aca2\","
            + "            \"domainName\": \"EmlacoreTenantDBLogin\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/4136781e-d91c-4d69-976e-d7c298c4aca2\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantid\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"9c3a576f-736e-4d23-b106-e26d4bce8adf\","
            + "            \"domainName\": \"TenantSchemaMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/9c3a576f-736e-4d23-b106-e26d4bce8adf\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantID\""
            + "                },"
            + "                {"
            + "                    \"name\": \"serviceName\""
            + "                },"
            + "                {"
            + "                    \"name\": \"serviceVersion\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"f4de97e5-47b4-4d43-ad33-322429897f1b\","
            + "            \"domainName\": \"CloudDatabaseResources\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/f4de97e5-47b4-4d43-ad33-322429897f1b\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"id\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"915c5cad-a175-4303-9c00-f47bd670cd78\","
            + "            \"domainName\": \"LogAnalyticsSolrOSSResources\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/915c5cad-a175-4303-9c00-f47bd670cd78\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"credentials\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"98d39323-cf21-42e3-ada5-bd1e6247e587\","
            + "            \"domainName\": \"DataServiceDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/98d39323-cf21-42e3-ada5-bd1e6247e587\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"PropertyBag\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"82af926b-6502-49f7-a12d-3d917d6320cc\","
            + "            \"domainName\": \"HAHDFSDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/82af926b-6502-49f7-a12d-3d917d6320cc\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"NameNode\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"9423b075-9a12-4293-bccb-85541df80652\","
            + "            \"domainName\": \"ITADatabaseTenantMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/9423b075-9a12-4293-bccb-85541df80652\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"tenantid\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"7d10280e-c174-48cf-b911-acce1c539daa\","
            + "            \"domainName\": \"zkBackupUtilityDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/7d10280e-c174-48cf-b911-acce1c539daa\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"zookeeperCluster\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"86a7a003-ce0a-4b1a-8fc4-aa2496b6189f\","
            + "            \"domainName\": \"InternalTenantIdMap\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/86a7a003-ce0a-4b1a-8fc4-aa2496b6189f\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"opcTenantId\""
            + "                },"
            + "                {"
            + "                    \"name\": \"internalTenantId\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"2fbb32ea-aece-4fe3-b6df-430bd16a877f\","
            + "            \"domainName\": \"ApplicationShardTenantMapping\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/2fbb32ea-aece-4fe3-b6df-430bd16a877f\","
            + "            \"keys\": ["
            + "                {"
            + "                    \"name\": \"applicationShard\""
            + "                }"
            + "            ]"
            + "        },"
            + "        {"
            + "            \"uuid\": \"b1f390b3-c2e1-4c1b-9977-5cf133e44a47\","
            + "            \"domainName\": \"HAHDFSClusterDomain\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/b1f390b3-c2e1-4c1b-9977-5cf133e44a47\","
            + "            \"keys\": [" + "                {" + "                    \"name\": \"NodeFqhn\""
            + "                }" + "            ]" + "        }" + "    ]," + "    \"count\": 12" + "}";

    private final static String TENANT_LOOKUP_RESULT_EMPTY_APP_MAPPINGS = "{" + "    \"total\": 0," + "    \"items\": ["
            + "    ]," + "    \"count\": 0" + "}";

    private final static String TENANT_LOOKUP_RESULT_EMPTY_APP_MAPPING_ENTITY = "{"
            + "    \"total\": 1,"
            + "    \"items\": ["
            + "        {"
            + "            \"domainUuid\": \"58bfe535-7134-4cae-be4a-d6d3dcfdb4d8\","
            + "            \"domainName\": \"TenantApplicationMapping\","
            + "            \"uuid\": \"ea395485-3981-4fc3-94c0-900523f3ebd0\","
            + "            \"canonicalUrl\": \"http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains/58bfe535-7134-4cae-be4a-d6d3dcfdb4d8/lookups/ea395485-3981-4fc3-94c0-900523f3ebd0\","
            + "            \"keys\": [" + "                {" + "                    \"name\": \"opcTenantId\","
            + "                    \"value\": \"emaastesttenant1\"" + "                }" + "            ],"
            + "            \"values\": [" + "            ]," + "            \"hash\": -2083815310" + "        }" + "    ],"
            + "    \"count\": 1" + "}";

    // @formatter:off

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesEmptyAppMappingEntityS2(@Mocked RegistryLookupUtil anyUtil,
                                                                       @Mocked final RestClient anyClient, @Mocked final InstanceInfo anyInstanceInfo,
                                                                       @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);
                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;

                anyClient.getWithException(anyString, anyString, anyString);
                anyClient.getWithException(anyString, anyString, anyString);
                returns("");
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
        Assert.assertTrue(services == null || services.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesEmptyAppMappingsS2(@Mocked RegistryLookupUtil anyUtil,
                                                                  @Mocked final RestClient anyClient, @Mocked final InstanceInfo anyInstanceInfo,
                                                                  @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);

                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;
                anyClient.getWithException(anyString, anyString, anyString);
                returns(TENANT_LOOKUP_RESULT_EMPTY_APP_MAPPINGS);
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
        Assert.assertTrue(services == null || services.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesEmptyDomainS2(@Mocked RegistryLookupUtil anyUtil,
                                                             @Mocked final RestClient anyClient, @Mocked final InstanceInfo anyInstanceInfo,
                                                             @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();

        link.withHref("");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);

                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        Assert.assertTrue(services == null || services.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesEmptyAppMappItemsS2(@Mocked RegistryLookupUtil anyUtil,
                                                                   @Mocked final RestClient anyClient, @Mocked final InstanceInfo anyInstanceInfo,
                                                                   @Mocked final LookupManager anyLookupManager)
            throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);

                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;

                anyClient.getWithException(anyString, anyString, anyString);
                returns(TENANT_LOOKUP_RESULT_EMPTY_APP_MAPPING_ENTITY);
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
        Assert.assertTrue(services == null || services.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesEmptyTenantAppUrlS2(@Mocked RegistryLookupUtil anyUtil,
                                                                   @Mocked final RestClient anyClient,
                                                                   @Mocked final InstanceInfo anyInstanceInfo,
                                                                   @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);

                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;

                anyClient.getWithException(anyString, anyString, anyString);
                returns(ENTITY_NAMING_DOMAIN_EMPTY_TENANT_APP_URL, TENANT_LOOKUP_RESULT);
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
        Assert.assertTrue(services == null || services.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesIOExceptionS2(@Mocked RegistryLookupUtil anyUtil,
                                                             @Mocked final RestClient anyClient, @Mocked final JsonUtil anyJsonUtil,
                                                             @Mocked final InstanceInfo anyInstanceInfo,
                                                             @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);
                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;

                anyClient.getWithException(anyString, anyString, anyString);
//                JsonUtil.buildNormalMapper();
                result = new IOException();
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
        Assert.assertTrue(services == null || services.isEmpty());
    }
    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServices(@Mocked final ClientResponse clientResponse, @Mocked final UniformInterfaceException uniformInterfaceException, @Mocked RegistryLookupUtil anyUtil,
                                                @Mocked final RestClient anyClient, @Mocked final JsonUtil anyJsonUtil,
                                                @Mocked final InstanceInfo anyInstanceInfo,
                                                @Mocked final LookupManager anyLookupManager) throws Exception {
        final VersionedLink link = new VersionedLink();
        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        final List<ServiceRequestCollection> src = new ArrayList<>();
        ServiceRequestCollection serviceRequestCollection = new ServiceRequestCollection();
        OrderComponents orderComponents = new OrderComponents();
        ServiceComponent serviceComponent = new ServiceComponent();
        List<Component> listComponent = new ArrayList<>();
        Component component = new Component();
        component.setComponent_id("com-id");
        List<ComponentParameter> listCompenentParameter = new ArrayList<>();
        ComponentParameter componentParameter = new ComponentParameter();
        componentParameter.setKey("APPLICATION_EDITION");
        componentParameter.setValue("edition");
        listCompenentParameter.add(componentParameter);
        listComponent.add(component);
        component.setComponent_parameter(listCompenentParameter);
        serviceComponent.setComponent(listComponent);
        serviceComponent.setComponent_id("id");
        orderComponents.setServiceComponent(serviceComponent);
        serviceRequestCollection.setServiceType("OMC");
        serviceRequestCollection.setTrial(false);
        serviceRequestCollection.setOrderComponents(orderComponents);
        src.add(serviceRequestCollection);

        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);
                LookupManager.getInstance().getLookupClient().lookup((InstanceQuery) any);
                result = insList;
                anyInstanceInfo.getLinksWithProtocol(anyString, anyString);
                result = links;

                anyClient.getWithException(anyString, anyString, anyString);
                result = "resp";
                anyJsonUtil.buildNormalMapper();
                result = anyJsonUtil;
                anyJsonUtil.fromJsonToList(anyString, ServiceRequestCollection.class);
                result = src;
            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        //Assert.assertNull(services);
//        Assert.assertTrue(services == null || services.isEmpty());
    }
    @Test(groups = {"s1"})
    public void testIsMonitoringServiceOnly() {
        Assert.assertFalse(TenantSubscriptionUtil.isMonitoringServiceOnly(null));
        Assert.assertFalse(TenantSubscriptionUtil.isMonitoringServiceOnly(Arrays.asList("Monitoring", "ITA")));
        Assert.assertFalse(TenantSubscriptionUtil.isMonitoringServiceOnly(Arrays.asList(new String[]{null})));
        Assert.assertFalse(TenantSubscriptionUtil.isMonitoringServiceOnly(Arrays.asList("test")));
//        Assert.assertTrue(TenantSubscriptionUtil.isMonitoringServiceOnly(Arrays
//                .asList(ApplicationEditionConverter.ApplicationOPCName.APM.toString())));
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesNullTenantS2() {
        List<String> rtn = TenantSubscriptionUtil.getTenantSubscribedServices(null, new TenantSubscriptionInfo());
        //Assert.assertNull(rtn);
        Assert.assertTrue(rtn == null || rtn.isEmpty());
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesS2(@Mocked RegistryLookupUtil anyUtil, @Mocked final RestClient anyClient,
                                                  @Mocked final InstanceInfo anyInstanceInfo,
                                                  @Mocked final LookupManager anyLookupManager,
                                                  @Mocked final RetryableLookupClient retryableLookupClient) throws Exception {
        final VersionedLink link = new VersionedLink();

        link.withHref("http://den00zyr.us.oracle.com:7007/naming/entitynaming/v1/domains");
        link.withRel("");
        final List<VersionedLink> links = Arrays.asList(link);
        final List<InstanceInfo> insList = new ArrayList<InstanceInfo>();
        insList.add(anyInstanceInfo);
        new Expectations() {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);

                retryableLookupClient.connectAndDoWithRetry(anyString, anyString, anyString, anyBoolean, anyString, (RetryableLookupClient.RetryableRunner) any);
                result = Arrays.asList("APM", "LogAnalytics", "ITAnalytics");

            }
        };
        List<String> services = TenantSubscriptionUtil.getTenantSubscribedServices("emaastesttenant1", new TenantSubscriptionInfo());
        Assert.assertEquals(services, Arrays.asList("APM", "LogAnalytics", "ITAnalytics"));
    }

    @Test(groups = {"s2"})
    public void testGetTenantSubscribedServicesString(@Mocked final RetryableLookupClient retryableLookupClient) {
        new NonStrictExpectations(TenantSubscriptionUtil.class) {
            {
                Deencapsulation.setField(TenantSubscriptionUtil.class, "IS_TEST_ENV", null);
                retryableLookupClient.connectAndDoWithRetry(anyString, anyString, anyString, anyBoolean, anyString, (RetryableLookupClient.RetryableRunner) any);
                returns(Arrays.asList("APM", "Compliance"), null, Arrays.asList());
            }
        };
        String apps = TenantSubscriptionUtil.getTenantSubscribedServicesString("emaastesttenant1");
        Assert.assertEquals(apps, "{\"applications\":[\"APM\",\"Compliance\"]}");

        apps = TenantSubscriptionUtil.getTenantSubscribedServicesString("emaastesttenant1");
        Assert.assertEquals(apps, "{\"applications\":[]}");

        apps = TenantSubscriptionUtil.getTenantSubscribedServicesString("emaastesttenant1");
        Assert.assertEquals(apps, "{\"applications\":[]}");

    }

    @Test(groups = {"s1"})
    public void testIsAPMServiceOnly() {
        Assert.assertFalse(TenantSubscriptionUtil.isAPMServiceOnly(null));
        Assert.assertFalse(TenantSubscriptionUtil.isAPMServiceOnly(Arrays.asList("APM", "ITA")));
        Assert.assertFalse(TenantSubscriptionUtil.isAPMServiceOnly(Arrays.asList(new String[]{null})));
        Assert.assertFalse(TenantSubscriptionUtil.isAPMServiceOnly(Arrays.asList("test")));
        Assert.assertTrue(TenantSubscriptionUtil.isAPMServiceOnly(Arrays
                .asList(ApplicationEditionConverter.ApplicationOPCName.APM.toString())));
    }

    private void cleanCache() {

        ICacheManager cm = CacheManagers.getInstance().build();
        Tenant cacheTenant = new Tenant("emaastesttenant1");
        cm.getCache(CacheConstants.CACHES_SUBSCRIBED_SERVICE_CACHE).evict(DefaultKeyGenerator.getInstance().generate(cacheTenant, new Keys(CacheConstants.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS)));
        cm.getCache(CacheConstants.CACHES_DOMAINS_DATA_CACHE).evict(DefaultKeyGenerator.getInstance().generate(cacheTenant, new Keys(ENTITY_NAMING_DOMAINS_URL)));
        cm.getCache(CacheConstants.CACHES_DOMAINS_DATA_CACHE).evict(DefaultKeyGenerator.getInstance().generate(cacheTenant, new Keys(TENANT_LOOKUP_URL)));
    }

    @AfterMethod(groups = {"s2"})
    public void afterMethod() {
        cleanCache();
    }

    @BeforeMethod(groups = {"s2"})
    public void beforeMethod() throws Exception {
        cleanCache();
    }
}
