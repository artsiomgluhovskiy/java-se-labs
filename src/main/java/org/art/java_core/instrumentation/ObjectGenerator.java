package org.art.java_core.instrumentation;

import gnu.trove.set.hash.TCharHashSet;
import gnu.trove.set.hash.TIntHashSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class ObjectGenerator {

    private static final Logger LOG = LogManager.getLogger(ObjectGenerator.class);

    public static Set<String> generateStringSetFromFile(String path) {
        Set<String> stringSet = new HashSet<>();
        try (InputStream in = Class.class.getResourceAsStream(path);
             LineIterator lineIterator = IOUtils.lineIterator(in, StandardCharsets.UTF_8)) {
            while (lineIterator.hasNext()) {
                stringSet.add(lineIterator.nextLine());
            }
        } catch (Exception e) {
            LOG.error("Exception while reading the file with path: {}", path, e);
        }
        return stringSet;
    }

    public static Set<Integer> toIntHashSet(Set<String> set) {
        Set<Integer> hashSet = new HashSet<>();
        for (String s : set) {
            hashSet.add(s.hashCode());
        }
        return hashSet;
    }

    public static TIntHashSet toTIntHashSet(Set<String> set) {
        TIntHashSet intHashSet = new TIntHashSet();
        for (String s : set) {
            intHashSet.add(s.hashCode());
        }
        return intHashSet;
    }
}
