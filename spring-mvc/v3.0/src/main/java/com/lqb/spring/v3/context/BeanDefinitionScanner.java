package com.lqb.spring.v3.context;

import com.lqb.spring.v3.beans.BeanDefinitionRegistry;
import com.lqb.spring.v3.beans.config.BeanDefinition;
import com.lqb.spring.v3.beans.config.BeanDefinitionHolder;
import com.lqb.spring.v3.core.io.Resource;
import com.lqb.spring.v3.core.io.support.ResourcePatternResolver;
import com.lqb.spring.v3.core.type.MetadataReader;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class BeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private ResourcePatternResolver resourcePatternResolver;

    public BeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinitionHolder> scan(String configLocation) {
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
        try {
            Set<BeanDefinition> candidates = findCandidateComponents(configLocation);
            for (BeanDefinition candidate : candidates) {
                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, candidate.getBeanClassName());
                beanDefinitions.add(definitionHolder);
                registerBeanDefinition(definitionHolder, this.registry);
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O failure during classpath scanning", e);
        }
        return beanDefinitions;
    }

    private void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        String beanName = definitionHolder.getBeanName();
        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) throws IOException {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        //解析com.lqb.xxx为com/lqb/web/demo/**/*.class
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + '/' + "**/*.class";
        Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = new MetadataReader(resource);
                BeanDefinition bd = new BeanDefinition(metadataReader);
                bd.setResource(resource);
                candidates.add(bd);
            }
            catch (Throwable ex) {
                throw new RuntimeException(
                        "Failed to read candidate component class: " + resource, ex);
            }
        }
        return candidates;
    }

    private MetadataReader getMetadataReader(Resource resource) {
        return null;
    }

    private boolean isCandidateComponent(MetadataReader metadataReader) {
        return true;
    }

    private String resolveBasePackage(String basePackage) {
        return basePackage.replace('.', '/');
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new ResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }
}
