# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'hale-bopp'
copyright = '2024, Illia Savenko'
author = 'Illia Savenko'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = []

templates_path = ['_templates']
exclude_patterns = []

extensions = [
    "sphinxcontrib.plantuml",
]

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

plantuml = f'plantuml'
plantuml_output_format = 'svg'
plantuml_args = ['-DRELATIVE_INCLUDE=true']


html_theme = 'alabaster'
html_static_path = ['_static']
