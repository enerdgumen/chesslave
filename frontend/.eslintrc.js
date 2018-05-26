module.exports = {
  extends: [
    "eslint:recommended",
    "plugin:react/recommended"
  ],
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
    node: true
  },
  globals: {
    __static: true
  },
  rules: {
    "no-console": "warn",
    "semi": ["warn", "never"],
    "react/prop-types": "off",
  },
}