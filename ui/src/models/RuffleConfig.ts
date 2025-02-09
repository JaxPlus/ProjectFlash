export interface RuffleConfig {
    allowScriptAccess?: boolean;
    parameters?: null | string | URLSearchParams | Record<string, string>;
    autoplay?: "on" | "off" | "auto";
    backgroundColor?: null | string;
    letterbox?: "off" | "fullscreen" | "on";
    unmuteOverlay?: "visible" | "hidden";
    upgradeToHttps?: boolean;
    compatibilityRules?: boolean;
    favorFlash?: boolean;
    warnOnUnsupportedContent?: boolean;
    logLevel?: "error" | "warn" | "info" | "debug" | "trace";
    showSwfDownload?: boolean;
    contextMenu?: "on" | "rightClickOnly" | "off";
    preloader?: boolean;
    splashScreen?: boolean;
    maxExecutionDuration?: number | { secs: number; nanos: number };
    base?: null | string;
    menu?: boolean;
    salign?: string;
    forceAlign?: boolean;
    quality?: string;
    scale?: string;
    forceScale?: boolean;
    allowFullscreen?: boolean;
    frameRate?: null | number;
    wmode?: "window" | "opaque" | "transparent" | "direct" | "gpu";
    playerVersion?: null | number;
    preferredRenderer?: null | "webgpu" | "wgpu-webgl" | "webgl" | "canvas";
    publicPath?: null | string;
    polyfills?: boolean;
    openUrlMode?: "allow" | "confirm" | "deny";
    allowNetworking?: "all" | "internal" | "none";
    openInNewTab?: null | ((swf: URL) => void);
    socketProxy?: { host: string; port: number; proxyUrl: string }[];
    fontSources?: string[];
    defaultFonts?: {
        [key: string]: string[] | undefined;
    };
    credentialAllowList?: string[];
    playerRuntime?: "air" | "flashPlayer";
}

interface DataLoadOptions {
    data: ArrayLike<number> | ArrayBufferLike;
    swfFileName?: string;
    allowScriptAccess?: boolean;
    parameters?: null | string | URLSearchParams | Record<string, string>;
    // autoplay?: AutoPlay;
    backgroundColor?: null | string;
    // letterbox?: Letterbox;
    // unmuteOverlay?: UnmuteOverlay;
    upgradeToHttps?: boolean;
    compatibilityRules?: boolean;
    favorFlash?: boolean;
    warnOnUnsupportedContent?: boolean;
    // logLevel?: LogLevel;
    showSwfDownload?: boolean;
    // contextMenu?: boolean | ContextMenu;
    preloader?: boolean;
    splashScreen?: boolean;
    // maxExecutionDuration?: Duration;
    base?: null | string;
    menu?: boolean;
    salign?: string;
    forceAlign?: boolean;
    quality?: string;
    scale?: string;
    forceScale?: boolean;
    allowFullscreen?: boolean;
    frameRate?: null | number;
    // wmode?: WindowMode;
    playerVersion?: null | number;
    // preferredRenderer?: null | RenderBackend;
    publicPath?: null | string;
    polyfills?: boolean;
    // openUrlMode?: OpenURLMode;
    // allowNetworking?: NetworkingAccessMode;
    // openInNewTab?: null | (swf: URL) => void;
    // socketProxy?: SocketProxy[];
    fontSources?: string[];
    // defaultFonts?: DefaultFonts;
    credentialAllowList?: string[];
    // playerRuntime?: PlayerRuntime;
}

export interface URLLoadOptions {
    url: string;
    allowScriptAccess?: boolean;
    parameters?: null | string | URLSearchParams | Record<string, string>;
    // autoplay?: AutoPlay;
    backgroundColor?: null | string;
    // letterbox?: Letterbox;
    // unmuteOverlay?: UnmuteOverlay;
    upgradeToHttps?: boolean;
    compatibilityRules?: boolean;
    favorFlash?: boolean;
    warnOnUnsupportedContent?: boolean;
    // logLevel?: LogLevel;
    showSwfDownload?: boolean;
    // contextMenu?: boolean | ContextMenu;
    preloader?: boolean;
    splashScreen?: boolean;
    // maxExecutionDuration?: Duration;
    base?: null | string;
    menu?: boolean;
    salign?: string;
    forceAlign?: boolean;
    quality?: string;
    scale?: string;
    forceScale?: boolean;
    allowFullscreen?: boolean;
    frameRate?: null | number;
    // wmode?: WindowMode;
    playerVersion?: null | number;
    // preferredRenderer?: null | RenderBackend;
    publicPath?: null | string;
    polyfills?: boolean;
    // openUrlMode?: OpenURLMode;
    // allowNetworking?: NetworkingAccessMode;
    // openInNewTab?: null | (swf: URL) => void;
    // socketProxy?: SocketProxy[];
    fontSources?: string[];
    // defaultFonts?: DefaultFonts;
    credentialAllowList?: string[];
    // playerRuntime?: PlayerRuntime;
}

interface PlayerV1 {
    addFSCommandHandler(
        handler: (command: string, args: string) => void,
    ): void;
    config: object | URLLoadOptions | DataLoadOptions;
    loadedConfig: null | URLLoadOptions | DataLoadOptions;
    get readyState(): ReadyState;
    // get metadata(): null | MovieMetadata;
    reload(): Promise<void>;
    load(options: string | URLLoadOptions | DataLoadOptions): Promise<void>;
    get volume(): number;
    // set volume(value: number): void;
    get fullscreenEnabled(): boolean;
    get isFullscreen(): boolean;
    requestFullscreen(): void;
    exitFullscreen(): void;
    get suspended(): boolean;
    suspend(): void;
    resume(): void;
    // set traceObserver(observer: null | (message: string) => void): void;
    downloadSwf(): Promise<void>;
    displayMessage(message: string): void;
    callExternalInterface(name: string, ...args: unknown[]): unknown;
}

export type APIVersions = {
    1: PlayerV1;
};

export interface PlayerElement extends Node {
    ruffle<V extends 1 = 1>(version?: V): APIVersions[V];
    onFSCommand: ((command: string, args: string) => void) | null;
    config: object | URLLoadOptions | DataLoadOptions;
    loadedConfig: null | URLLoadOptions | DataLoadOptions;
    get readyState(): ReadyState;
    // get metadata(): null | MovieMetadata;
    reload(): Promise<void>;
    load(options: string | URLLoadOptions | DataLoadOptions): Promise<void>;
    play(): void;
    get isPlaying(): boolean;
    get volume(): number;
    set volume(value: number);
    get fullscreenEnabled(): boolean;
    get isFullscreen(): boolean;
    setFullscreen(isFull: boolean): void;
    enterFullscreen(): void;
    exitFullscreen(): void;
    pause(): void;
    set traceObserver(observer: ((message: string) => void) | null);
    downloadSwf(): Promise<void>;
    displayMessage(message: string): void;
    PercentLoaded(): number;
    style: CSSStyleDeclaration;
}

export interface SourceAPI {
    version: string;
    polyfill(): void;
    pluginPolyfill(): void;
    createPlayer(): PlayerElement;
}

declare global {
    interface Window {
        RufflePlayer: {
            config: RuffleConfig;
            newest(): SourceAPI;
        };
    }
}