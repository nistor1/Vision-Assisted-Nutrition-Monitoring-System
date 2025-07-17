export function getRedirectedPath(search: string): string {
    const params = new URLSearchParams(search);
    const redirect = params.get('redirect');
    return redirect || '/';
}
