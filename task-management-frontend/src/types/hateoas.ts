// Basic link
export interface HateoasLink {
  href: string
}

// Links
export type HateoasLinks = Record<string, HateoasLink>

// Entity model
export interface EntityModel<T> {
  content?: T
  _links?: HateoasLinks
  _embedded?: Record<string, T | T[]>
}

// Collection model
export interface CollectionModel<T> {
  _embedded: Record<string, T[]>
  _links: HateoasLinks
}

// Page metadata
export interface PageMetadata {
  size: number
  totalElements: number
  totalPages: number
  number: number
}

// Paged model
export interface PagedModel<T> {
  _embedded: Record<string, T[]>
  _links: HateoasLinks
  page: PageMetadata
}

// Extract single entity
export function unwrapEntity<T>(model: EntityModel<T>): T {
  // direct content
  if (model.content) return model.content

  // embedded
  if (model._embedded) {
    const keys = Object.keys(model._embedded)
    if (keys.length > 0) {
      const key = keys[0]!
      const value = model._embedded[key]
      if (Array.isArray(value)) {
        return (value[0] ?? null) as T
      }
      return value as T
    }
  }

  // fallback
  return model as unknown as T
}

// Extract collection
export function unwrapCollection<T>(model: CollectionModel<EntityModel<T>>): T[] {
  const keys = Object.keys(model._embedded)
  if (keys.length === 0) return []

  if (keys.length === 0) return []

  const key = keys[0]!
  const entityModels = model._embedded[key]

  if (!entityModels) return []

  return entityModels.map((em) => em.content ?? ({} as T))
}

// Extract paged model
export interface UnwrappedPaged<T> {
  items: T[]
  page: PageMetadata
  links: HateoasLinks
}

export function unwrapPaged<T>(model: PagedModel<T>): UnwrappedPaged<T> {
  if (!model._embedded) {
    return {
      items: [],
      page: model.page,
      links: model._links,
    }
  }

  const keys = Object.keys(model._embedded)
  if (keys.length === 0) {
    return {
      items: [],
      page: model.page,
      links: model._links,
    }
  }

  const key = keys[0]!
  const items = model._embedded[key] ?? []

  return {
    items,
    page: model.page,
    links: model._links,
  }
}
