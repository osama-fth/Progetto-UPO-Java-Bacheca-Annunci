# Config
SRC_DIR := Progetto_UPO_Java_Foutih_20054809/src
PROJECT_ROOT := $(patsubst %/src,%,$(SRC_DIR))
BUILD_DIR := $(PROJECT_ROOT)/bin
DIST_DIR := dist
JAR_NAME := bacheca-annunci.jar

JAVA := java
JAVAC := javac
JAR := jar

# Tutti i sorgenti tranne i test
SRC_FILES := $(shell find $(SRC_DIR) -name "*.java" ! -path "$(SRC_DIR)/modello/test/*")

.PHONY: all compile jar run clean clean-all

all: jar

compile:
	@mkdir -p $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -sourcepath $(SRC_DIR) $(SRC_FILES)

prepare-resources:
	@mkdir -p $(DIST_DIR)/src
	@cp "$(SRC_DIR)/annunci.txt" "$(DIST_DIR)/src/annunci.txt" || true

jar: compile prepare-resources
	@mkdir -p $(DIST_DIR)
	$(JAR) cfm $(DIST_DIR)/$(JAR_NAME) MANIFEST.MF -C $(BUILD_DIR) .

run:
	@cd $(DIST_DIR) && $(JAVA) -jar $(JAR_NAME)

clean:
	@rm -rf $(BUILD_DIR)

clean-all: clean
	@rm -rf $(DIST_DIR)
