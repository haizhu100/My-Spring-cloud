package com.cheng.springcloud.sdk.core.swagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
public class Swagger2 extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	/**
	 * Bean factory for this context
	 */
	private ConfigurableListableBeanFactory beanFactory;

	public final static String X_API_ID = "x-api-id";

	public final static String X_AUDIENCE = "x-audience";

	public final static String COMPONENT_INTERNAL = "component-internal";

	public final static String SYSTEM_INTERNAL = "system-internal";

	public final static String COMPANY_INTERNAL = "company-internal";

	public final static String EXTERNAL_PARTNER = "external-partner";

	public final static String EXTERNAL_PUBLIC = "external-public";

	@Autowired
	SwaggerProperties paasSwaggerProperties;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            beanFactory = ((AbstractRefreshableApplicationContext) applicationContext).getBeanFactory();
        } else {
            beanFactory = ((GenericApplicationContext) applicationContext).getBeanFactory();
        }
	}

	@PostConstruct
	public void init() {
		Map<String,SwaggerDocketProperties> dockets = paasSwaggerProperties.getDockets();
		for (String docket : dockets.keySet()) {
			beanFactory.registerSingleton(dockets.get(docket).getGroupName(),
					getDocket(paasSwaggerProperties, dockets.get(docket)));
		}
	}

	private Docket getDocket(SwaggerProperties paasSwaggerProperties,
			SwaggerDocketProperties paasSwaggerDocketProperties) {
		Docket docket = chooseDocumentationType(paasSwaggerProperties.getDocumentationType());
		if (paasSwaggerDocketProperties.getGroupName() != null) {
			docket.groupName(paasSwaggerDocketProperties.getGroupName());
		}
		if (paasSwaggerDocketProperties.getHost() != null) {
			docket.host(paasSwaggerDocketProperties.getHost());
		}
		docket.apiInfo(apiInfo(paasSwaggerDocketProperties.getApiinfo())).select()
				.apis(RequestHandlerSelectors.basePackage(paasSwaggerDocketProperties.getBasePackage()))
				.paths(toPathSelector(paasSwaggerDocketProperties)).build();
		return docket;
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> toPathSelector(SwaggerDocketProperties paasSwaggerDocketProperties) {
		if (paasSwaggerDocketProperties.getPathSelectors() == null
				|| paasSwaggerDocketProperties.getPathSelectors().isEmpty()) {
			return PathSelectors.any();
		}
		Predicate<String> includePaths = paasSwaggerDocketProperties.getPathSelectors().getIncludePatterns().stream()
				.map(PathSelectors::ant).reduce(Predicates::or).orElse(Predicates.alwaysTrue());
		Predicate<String> excludePaths = paasSwaggerDocketProperties.getPathSelectors().getExcludePatterns().stream()
				.map(e -> Predicates.not(PathSelectors.ant(e))).reduce(Predicates::or).orElse(null);
		return excludePaths == null ? Predicates.and(includePaths) : Predicates.and(includePaths, excludePaths);
	}

	private ApiInfo apiInfo(SwaggerApiInfoProperties paasSwaggerApiInfoProperties) {

		@SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtension = new ArrayList<>();
		Map<String, String> contactMap = paasSwaggerApiInfoProperties.getContact();
		ApiInfoBuilder apiInfo = new ApiInfoBuilder();

		if (paasSwaggerApiInfoProperties.getTitle() != null) {
			apiInfo.title(paasSwaggerApiInfoProperties.getTitle());
		}
		if (paasSwaggerApiInfoProperties.getDescription() != null) {
			apiInfo.description(paasSwaggerApiInfoProperties.getDescription());
		}
		if (contactMap != null) {
			Contact contact = new Contact(contactMap.get("contactname"), contactMap.get("contacturl"),
					contactMap.get("contactemail"));
			apiInfo.contact(contact);
		}
		if (paasSwaggerApiInfoProperties.getTermsOfServiceUrl() != null) {
			apiInfo.termsOfServiceUrl(paasSwaggerApiInfoProperties.getTermsOfServiceUrl());
		}
		if (paasSwaggerApiInfoProperties.getLicense() != null) {
			apiInfo.license(paasSwaggerApiInfoProperties.getLicense());
		}
		if (paasSwaggerApiInfoProperties.getLicenseUrl() != null) {
			apiInfo.licenseUrl(paasSwaggerApiInfoProperties.getLicenseUrl());
		}
		if (paasSwaggerApiInfoProperties.getVersion() != null) {
			apiInfo.version(paasSwaggerApiInfoProperties.getVersion());
		}

		if (paasSwaggerApiInfoProperties.getxAudience() != null) {
			StringVendorExtension audience = new StringVendorExtension(X_AUDIENCE,
					paasSwaggerApiInfoProperties.getxAudience());
			vendorExtension.add(audience);
		}
		if (paasSwaggerApiInfoProperties.getxApiId() != null) {
			StringVendorExtension apiId = new StringVendorExtension(X_API_ID, paasSwaggerApiInfoProperties.getxApiId());
			vendorExtension.add(apiId);

		}
		apiInfo.extensions(vendorExtension);
		return apiInfo.build();
	}

	public Docket chooseDocumentationType(String documentationType) {
		Docket docket = null;
		switch (documentationType) {
		case "SWAGGER_2":
			docket = new Docket(DocumentationType.SWAGGER_2);
			break;
		case "SWAGGER_12":
			docket = new Docket(DocumentationType.SWAGGER_12);
			break;
		case "SPRING_WEB":
			docket = new Docket(DocumentationType.SPRING_WEB);
			break;
		}
		return docket;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	enum PaasBaseApiAudienceEnum {
		COMPONENT_INTERNAL, SYSTEM_INTERNAL, COMPANY_INTERNAL, EXTERNAL_PARTNER, EXTERNAL_PUBLIC;

	}

}
