
import Vue from 'vue';
import { message } from './resetMessage'
import {
  Pagination,
  Dialog,
  Autocomplete,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  Menu,
  Submenu,
  MenuItem,
  MenuItemGroup,
  Input,
  InputNumber,
  Radio,
  RadioGroup,
  RadioButton,
  Checkbox,
  CheckboxButton,
  CheckboxGroup,
 
  Select,
  Option,
  OptionGroup,
  Button,
  ButtonGroup,
  Table,
  TableColumn,
  DatePicker,
  TimeSelect,
  TimePicker,
  Popover,
  Tooltip,
  // Breadcrumb,
  // BreadcrumbItem,
  Form,
  FormItem,
  Tabs,
  TabPane,
  Tag,
  Tree,
  Alert,
  Icon,
  Row,
  Col,
  Upload,
  Spinner,
  Badge,
  Card,
  Step,
  Carousel,
  CarouselItem,
  Collapse,
  CollapseItem,
  Cascader,
  ColorPicker,
  Container,
  Header,
  Aside,
  Main,
  Footer,
  Link,
  Divider,
  Image,
  Backtop,
  PageHeader,
  CascaderPanel,
  Loading,
  MessageBox,
  Notification,
  Empty,
  Skeleton,
  SkeletonItem,
  Avatar,
  // Timeline,
  // TimelineItem,
  Switch,
  // Slider,
  // Rate,
  // Transfer,
  // Progress,
  Descriptions,
  DescriptionsItem,
  Drawer,
  Steps,
  // Calendar,


} from 'element-ui';
Vue.use(Empty);
Vue.use(Pagination);
Vue.use(Dialog);
Vue.use(Autocomplete);
Vue.use(Dropdown);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Menu);
Vue.use(Submenu);
Vue.use(MenuItem);
Vue.use(MenuItemGroup);
Vue.use(Input);
Vue.use(InputNumber);
Vue.use(Radio);
Vue.use(RadioGroup);
Vue.use(RadioButton);
Vue.use(Checkbox);
Vue.use(CheckboxButton);
Vue.use(CheckboxGroup);
Vue.use(Drawer)
Vue.use(Select);
Vue.use(Option);
Vue.use(OptionGroup);
Vue.use(Button);
Vue.use(ButtonGroup);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(DatePicker);
Vue.use(TimeSelect);
Vue.use(TimePicker);
Vue.use(Popover);
Vue.use(Tooltip);
// Vue.use(Breadcrumb);
// Vue.use(BreadcrumbItem);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Tabs);
Vue.use(TabPane);
Vue.use(Tag);
Vue.use(Tree);
Vue.use(Alert);
Vue.use(Icon);
Vue.use(Row);
Vue.use(Col);
Vue.use(Upload);
Vue.use(Spinner);
Vue.use(Badge);
Vue.use(Card);
Vue.use(Step);
Vue.use(Carousel);
Vue.use(CarouselItem);
Vue.use(Collapse);
Vue.use(CollapseItem);
Vue.use(Cascader);
Vue.use(ColorPicker);
Vue.use(Container);
Vue.use(Header);
Vue.use(Aside);
Vue.use(Main);
Vue.use(Footer);
Vue.use(Link);
Vue.use(Divider);
Vue.use(Image);
Vue.use(Backtop);
Vue.use(PageHeader);
Vue.use(CascaderPanel);
Vue.use(Skeleton);
Vue.use(SkeletonItem);

Vue.use(Avatar)
Vue.use(Loading.directive);
// Vue.use(Timeline);
// Vue.use(TimelineItem);
Vue.use(Switch);
// Vue.use(Rate);
// Vue.use(Slider);
// Vue.use(Transfer);
// Vue.use(Progress);
Vue.use(Descriptions)
Vue.use(DescriptionsItem)
Vue.use(Steps);
// Vue.use(Calendar);

// Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$alert = MessageBox.alert;
Vue.prototype.$confirm = MessageBox.confirm;
// Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$notify = Notification;
Vue.prototype.$message = message;

// Vue.prototype.$loading = Loading.service;
