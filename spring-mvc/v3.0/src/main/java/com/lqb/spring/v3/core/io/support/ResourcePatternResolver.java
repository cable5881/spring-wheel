package com.lqb.spring.v3.core.io.support;

import com.lqb.spring.v3.core.io.Resource;
import com.lqb.spring.v3.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ResourcePatternResolver {

    public static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    public Resource[] getResources(String locationPattern) throws IOException {
        if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            // a class path resource (multiple resources for same name possible)
            if (isPattern(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()))) {
                // a class path resource pattern
                return findPathMatchingResources(locationPattern);
            }
            else {
                // all class path resources with the given name
                return findAllClassPathResources(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()));
            }
        }
        else {
            return null;
        }
    }

    protected Resource[] findAllClassPathResources(String location) throws IOException {
        String path = location;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        Set<Resource> result = doFindAllClassPathResources(path);
        return result.toArray(new Resource[0]);
    }

    private Set<Resource> doFindAllClassPathResources(String path) throws IOException {
        Set<Resource> result = new LinkedHashSet<>(16);
        ClassLoader cl = this.getClass().getClassLoader();
        Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            result.add(convertClassLoaderURL(url));
        }
        return result;
    }

    private Resource convertClassLoaderURL(URL url) {
        return new Resource(url);
    }

    protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
        String rootDirPath = determineRootDir(locationPattern);
        String subPattern = locationPattern.substring(rootDirPath.length());
        Resource[] rootDirResources = getResources(rootDirPath);
        Set<Resource> result = new LinkedHashSet<>(16);
        for (Resource rootDirResource : rootDirResources) {
            //暂时不考虑jar等格式
            result.addAll(doFindPathMatchingFileResources(rootDirResource, subPattern));
        }
        return result.toArray(new Resource[0]);
    }

    protected Set<Resource> doFindPathMatchingFileResources(Resource rootDirResource, String subPattern) throws IOException {
        File rootDir;
        try {
            rootDir = rootDirResource.getFile().getAbsoluteFile();
        }
        catch (Exception ex) {
            return Collections.emptySet();
        }
        return doFindMatchingFileSystemResources(rootDir, subPattern);
    }

    protected Set<Resource> doFindMatchingFileSystemResources(File rootDir, String subPattern) throws IOException {
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
        Set<Resource> result = new LinkedHashSet<>(matchingFiles.size());
        for (File file : matchingFiles) {
            result.add(new Resource(file));
        }
        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
        if (!rootDir.exists()) {
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            return Collections.emptySet();
        }
        String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(), File.separator, "/");
        if (!pattern.startsWith("/")) {
            fullPattern += "/";
        }
        fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/");
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(fullPattern, rootDir, result);
        return result;
    }

    protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) {
        for (File content : listDirectory(dir)) {
            String currPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
            if (content.isDirectory() && matchStart(fullPattern, currPath + "/")) {
                if (content.canRead()) {
                    doRetrieveMatchingFiles(fullPattern, content, result);
                }
            }
            if (match(fullPattern, currPath)) {
                result.add(content);
            }
        }
    }

    private boolean match(String pattern, String path) {
        return doMatch(pattern, path, true, null);
    }
    public boolean matchStart(String pattern, String path) {
        return doMatch(pattern, path, false, null);
    }
    private boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        return true;
    }


    protected File[] listDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return new File[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        return files;
    }

    private String determineRootDir(String location) {
        int prefixEnd = location.indexOf(':') + 1;
        int rootDirEnd = location.length();
        while (rootDirEnd > prefixEnd && isPattern(location.substring(prefixEnd, rootDirEnd))) {
            rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
        }
        if (rootDirEnd == 0) {
            rootDirEnd = prefixEnd;
        }
        return location.substring(0, rootDirEnd);
    }

    private boolean isPattern(String path) {
        if (path == null) {
            return false;
        }
        boolean uriVar = false;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == '*' || c == '?') {
                return true;
            }
            if (c == '{') {
                uriVar = true;
                continue;
            }
            if (c == '}' && uriVar) {
                return true;
            }
        }
        return false;
    }
}
