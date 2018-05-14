def call() {
  // Using the MPL library of specific version and adding the custom path to find modules
  library('mpl@project-module-fix').com.griddynamics.devops.mpl.MPLManager.instance.addModulesLoadPath('com/company/nestedlib')
}
