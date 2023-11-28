const httpRegex = /^(http|https):\/\//;
const TargetBlankExtension = (md: any) => {
  const defaultRender =
    md.renderer.rules.link_open ||
    function (tokens: any, idx: number, options: any, env: any, self: any) {
      return self.renderToken(tokens, idx, options);
    };


  md.renderer.rules.link_open = function (tokens: any, idx: number, options: any, env: any, self: any) {
    const aIndex = tokens[idx].attrIndex("target");
    // console.log({ tokens, idx, options, env, self, aIndex, href: tokens[idx].attrGet("href") });

    if (aIndex < 0) {
      if (httpRegex.test(tokens[idx].attrGet("href"))) {
        tokens[idx].attrPush(["target", "_blank"]);
      }
    } else {
      tokens[idx].attrs[aIndex][1] = "_blank";
    }

    // pass token to default renderer.
    return defaultRender(tokens, idx, options, env, self);
  };

};

export default TargetBlankExtension;
