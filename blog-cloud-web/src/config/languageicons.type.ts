export interface DefaultIcon {
    /**
     * Name of the icon, e.g. 'src'
     */
    name: string;

    /**
     * Define if there is a light icon available.
     */
    light?: boolean;

    /**
     * Define if there is a high contrast icon available.
     */
    highContrast?: boolean;
}
/**
* Defines icon packs that can be toggled.
*/
export enum IconPack {
    Angular = 'angular',
    Nest = 'nest',
    Ngrx = 'angular_ngrx',
    React = 'react',
    Redux = 'react_redux',
    Vue = 'vue',
    Vuex = 'vue_vuex',
}

export interface LanguageIcon {
    /**
     * Icon for the language identifier
     */
    icon: DefaultIcon;

    /**
     * Language ID, e.g. 'javascript'
     *
     * According to official VS Code documentation:
     * https://code.visualstudio.com/docs/languages/identifiers
     */
    ids: string[];

    /**
     * Define if the icon should be disabled.
     */
    disabled?: boolean;

    /**
     * Defines a pack to which this icon belongs. A pack can be toggled and all icons inside this pack can be enabled or disabled together.
     */
    enabledFor?: IconPack[];
}
