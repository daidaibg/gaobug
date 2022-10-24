// 泛型
export type Rows<T extends any> = {
  items: T[];
};

export type TARGET_CONTAINER = HTMLElement | Window | undefined;

export interface BlogDetailsType {
  id?: string | string[],
  title?: string | number,
  author?:string | number,
  authorName?: string,
  createTime?: string,
  categoryName?: string,
  isAuthor?: string,
  coverUrl?: string,
  summary?: string,
  tag?: string,
  openComment?: number,
  collectCount?: number,
  clickCount?: number
}
export interface ActionProps {
  articleId?: BlogDetailsType["id"],
  likeNum?: BlogDetailsType["clickCount"],
  commentNum?: BlogDetailsType["openComment"],
  collectCount?: BlogDetailsType["collectCount"],
  onLike:Function
}

export interface CommentProps {
  articleId?: BlogDetailsType["id"],
  avatarUrl?: BlogDetailsType['coverUrl']
}

export type PreviewThemeType = 'default' | 'github' | 'vuepress' | 'mk-cute' | 'smart-blue' | 'cyanosis'

export type CodeTheme = 'atom'|'a11y'|'github'|'gradient'|'kimbie'|'paraiso'|'qtcreator'|'stackoverflow'