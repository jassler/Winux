echo 'package os.utils;

public class ObjectRelocEntrySizes {
    
    public static int getSizeOf(String str) {
' > src/os/utils/ObjectRelocEntrySizes.java

find src -name '*\.java' -exec basename {} \; | sed 's/.java//' | for i in $(cat); do printf '        if("%s".equals(str)) return MAGIC.getInstRelocEntries("%s");\n' $i $i; done >> src/os/utils/ObjectRelocEntrySizes.java

echo '
        return -1;
    }
}' >> src/os/utils/ObjectRelocEntrySizes.java