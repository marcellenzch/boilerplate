import { useEffect, useState } from 'react';
import { persistentStorage } from '@/lib/persistent-storage.ts';

export function usePersistentStorageValue<T>(key: string, initialValue?: T) {
  const [value, setValue] = useState<T>(() => {
    const valueFromStorage = persistentStorage.getItem(key);

    if (typeof initialValue === 'object' && !Array.isArray(initialValue) && initialValue !== null) {
      return {
        ...initialValue,
        ...valueFromStorage,
      };
    }

    return valueFromStorage || initialValue;
  });

  useEffect(() => {
    persistentStorage.setItem(key, value);
  }, [key, value]);

  return [value, setValue] as const;
}
