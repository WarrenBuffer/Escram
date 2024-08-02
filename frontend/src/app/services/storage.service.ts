import { isPlatformBrowser } from '@angular/common';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private readonly platformId = inject(PLATFORM_ID);
  
  constructor() { }

  getItem(itemName: string): string | null {
    if (isPlatformBrowser(this.platformId))
      return sessionStorage.getItem(itemName);

    return null;
  }
  
  setItem(itemName: string, itemValue: string) {
    if (isPlatformBrowser(this.platformId))
      sessionStorage.setItem(itemName, itemValue);
  }

  removeItem(itemName: string) {
    if (isPlatformBrowser(this.platformId))
      sessionStorage.removeItem(itemName);
  }
}
