#!/bin/bash

file_name=./api_keys.gradle

function ask_annict_application_id {
  echo "Type annict_application_id:"
  read answer
  if [ -z $answer ]; then
    ask_annict_application_id
  else
    annict_application_id=$answer
  fi
}

function ask_annict_secret_key {
  echo "Type annict_secret_key:"
  read answer
  if [ -z $answer ]; then
    ask_annict_secret_key
  else
    annict_secret_key=$answer
  fi
}

function createFile {
  if [ -f $file_name ]; then
    rm $file_name
  fi

  touch $file_name
  echo "ext.annict_application_id = '${annict_application_id}'" >> $file_name
  echo "ext.annict_secret_key = '${annict_secret_key}'" >> $file_name

  echo "${file_name} created !"
}

function generate {
  # Annict application id
  ask_annict_application_id

  # Annict secret key
  ask_annict_secret_key

  # Create file
  createFile
}

function ask_re_generate {
  read answer
  case $answer in
    y)
      generate
      ;;
    n)
      exit
      ;;
    *)
      echo -e "Type [y/n]"
      ask_re_generate
      ;;
   esac
}

if [ -f $file_name ]; then
  echo "${file_name} exists. Do you want to re-generate ? [y/n]"
  ask_re_generate
else
  echo "$file_name does not exist."
  generate
fi
