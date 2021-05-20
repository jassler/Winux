FILENAME=src/os/utils/ObjectEntrySizes.java
NOMATCH='Override|SJC|MAGIC'

echo 'package os.utils;
' > $FILENAME

find src -name '*.java' | sed 's/.java$/;/' | sed 's/src\//import /' | sed 's/\//./g' | grep -vE $NOMATCH >> $FILENAME

echo '
public class ObjectEntrySizes {

    public static int getInstRelocEntries(String str) {
' >> $FILENAME

#################
# RELOC ENTRIES #
#################
find src -name '*\.java' -exec basename {} \; | sed 's/.java//' | grep -vE $NOMATCH | for i in $(cat); do printf '        if("%s".equals(str)) return MAGIC.getInstRelocEntries("%s");\n' $i $i; done >> $FILENAME
echo '
        return -1;
    }

    public static int getInstScalarEntries(String str) {
' >> $FILENAME

##################
# SCALAR ENTRIES #
##################
find src -name '*\.java' -exec basename {} \; | sed 's/.java//' | grep -vE $NOMATCH | for i in $(cat); do printf '        if("%s".equals(str)) return MAGIC.getInstScalarSize("%s");\n' $i $i; done >> $FILENAME
echo '
        return -1;
    }

    public static void printPossibilities(Terminal out) {
' >> $FILENAME

#################
# PRINT OPTIONS #
#################
find src -name '*\.java' -exec basename {} \; | sed 's/.java//' | grep -vE $NOMATCH | for i in $(cat); do printf '        out.print("%s ");\n' $i; done >> $FILENAME
echo '
        out.println();
    }
}
' >> $FILENAME
