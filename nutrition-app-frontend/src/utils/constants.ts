export const API_CONFIG = {
    BASE_URL: 'http://localhost:8080',
    TIMEOUT: 10000,
  } as const;
  
  export const PAGINATION_DEFAULTS = {
    PAGE_SIZE: 10,
    MAX_PAGE_SIZE: 100,
  } as const;
  
  export const FORM_VALIDATION = {
    MIN_TAX_RATE: 0,
    MAX_TAX_RATE: 100,
  } as const;