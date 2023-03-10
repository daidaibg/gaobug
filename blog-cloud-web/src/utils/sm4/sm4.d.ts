type byte = number[] | Uint8Array | string
declare interface sm4 {
  encrypt: (inArray: byte, key: string, options?: any) => void,
  decrypt: (inArray: byte, key: string, options?: any) => void,
}
