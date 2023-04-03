export const createLink = (href: string, rel: string, sizes?: string) => {
    let link:any= document.createElement('link') as HTMLLinkElement ;
    link.rel = rel;
    if (sizes) {
        link.sizes = sizes;
    }
    link.href = href;
    document.head.appendChild(link);
}