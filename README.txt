# Self built operating system

1. Download executables from http://www.fam-frenz.de/stefan/compiler.html
2. Add everything from that folder to this github repository (all files are ignored from `.gitignore`)
3. Compile with `java -jar sjc.jar src -o boot`
4. Run with `qemu -m 32 -boot a -fda BOOT_FLP.IMG -L "C:\Program Files\qemu"`
