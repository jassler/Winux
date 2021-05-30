./objRelocEntriesGenerator.sh
java -jar sjc.jar src -t ia32 -u rte -T nsop -o boot -n && /usr/local/Cellar/qemu/6.0.0/bin/qemu-system-x86_64 -boot a -serial stdio -fda BOOT_FLP.IMG