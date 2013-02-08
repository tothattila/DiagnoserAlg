package org.diagnoser.model.xml.compact;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/8/13
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryLister {

    public List<String> listAllXmlsInDirectory(final String directory) {
        ArrayList<String> result = new ArrayList<String>();

        File dir = new File(directory);
        if (dir.isDirectory()) {
            File[] xmlFiles = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toUpperCase().endsWith(".XML");
                }
            });

            for (File f:xmlFiles) {
                result.add(directory+File.separator+f.getName());
            }
        }

        return result;
    }
}
