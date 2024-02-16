#!/bin/bash

# Check if package names are provided
if [ $# -eq 0 ]; then
  echo "Usage: $0 <package_name1> [<package_name2> ...]"
  exit 1
fi

# Iterate over each package name argument
for package_name in "$@"; do
  # Check if the package exists using adb shell pm list packages
  if adb shell pm list packages | grep -q "^package:$package_name$"; then
    echo "Uninstalling $package_name..."
    adb uninstall $package_name
    echo "Uninstallation of $package_name completed."
  else
    echo "The package $package_name is not installed."
  fi
done