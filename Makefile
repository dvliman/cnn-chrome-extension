.PHONY: release
release:
	-rm -rf resources/unpacked/out
	shadow-cljs release extension

.PHONY: watch
watch:
	shadow-cljs watch extension
