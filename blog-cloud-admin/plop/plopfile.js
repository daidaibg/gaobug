const tablePageTemplates= require("./table-page-templates/index")
module.exports = function (plop) {
  // create your generators here
  plop.setGenerator('tablePage',tablePageTemplates);
};