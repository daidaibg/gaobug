import en from '../language/en.json'
const enModules:any =import.meta.globEager('./*.ts') 
const modules:any = {}
for (const key in enModules) {
  modules[key.replace(/(\.\/|\.ts)/g, '')] = enModules[key].default
}
const global = {
  doc: 'Document',
  help: 'Help',
  contact: 'Contact Us',
  logout: 'Logout',

}

export default {
  global,
  ...en,
  ...modules
}
